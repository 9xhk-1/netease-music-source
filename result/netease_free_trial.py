#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
NetEase Cloud Music Wearable/XTC Watch Child Edition - Free Listening API client
This script implements EAPI protocol signature and encryption layer to interact with
NetEase watch endpoints, including guest login, captcha login, song favoriting,
and fetching play URLs with the red-heart bypass logic.

Dependencies:
    pip install requests pycryptodome
"""

import os
import sys
import time
import random
import json
import hashlib
import binascii
import argparse
import requests
from Crypto.Cipher import AES

# Key constants for NetEase EAPI
EAPI_AES_KEY = b"e82ckenh8dichen8"
EAPI_SALT_FORMAT = "nobody{url}use{text}md5forencrypt"


def pkcs7_pad(data: bytes) -> bytes:
    pad_len = 16 - (len(data) % 16)
    return data + bytes([pad_len] * pad_len)


def pkcs7_unpad(data: bytes) -> bytes:
    if not data:
        return b""
    pad_len = data[-1]
    if pad_len < 1 or pad_len > 16:
        return data
    # Check if all padded bytes have the same value
    for i in range(len(data) - pad_len, len(data)):
        if data[i] != pad_len:
            return data
    return data[:-pad_len]


def aes_encrypt(text: str, key: bytes = EAPI_AES_KEY) -> bytes:
    padded = pkcs7_pad(text.encode("utf-8"))
    cipher = AES.new(key, AES.MODE_ECB)
    return cipher.encrypt(padded)


def aes_decrypt(data: bytes, key: bytes = EAPI_AES_KEY) -> str:
    cipher = AES.new(key, AES.MODE_ECB)
    decrypted = cipher.decrypt(data)
    unpadded = pkcs7_unpad(decrypted)
    return unpadded.decode("utf-8", errors="ignore")


def eapi_encrypt(url_path: str, text: str) -> str:
    """
    Encrypt and sign parameters using standard EAPI protocol.
    Args:
        url_path (str): The api path (must start with /api/, NOT /eapi/)
        text (str): The payload JSON parameter string
    Returns:
        str: Hex-encoded ciphertext representing the signed 'params' field.
    """
    salt = EAPI_SALT_FORMAT.format(url=url_path, text=text)
    digest = hashlib.md5(salt.encode("utf-8")).hexdigest()
    message = f"{url_path}-36cd479b6b5-{text}-36cd479b6b5-{digest}"
    encrypted_bytes = aes_encrypt(message)
    return binascii.hexlify(encrypted_bytes).decode("utf-8").upper()


def eapi_decrypt(encrypted_data: bytes) -> str:
    """
    Decrypt server's binary or hex response.
    """
    try:
        return aes_decrypt(encrypted_data)
    except Exception:
        try:
            # If the response was hex encoded instead of raw binary
            raw_bytes = binascii.unhexlify(encrypted_data.strip())
            return aes_decrypt(raw_bytes)
        except Exception as e:
            return f"Decryption error: {str(e)}"


class NeteaseWearableAPI:
    def __init__(self, base_url="https://interface3.music.163.com"):
        self.base_url = base_url.rstrip("/")
        self.session = requests.Session()
        self.session.headers.update({
            "User-Agent": "Mozilla/5.0 (Android; Mobile) Xiaomi/XTC Child Watch",
            "Content-Type": "application/x-www-form-urlencoded",
            "Referer": self.base_url,
        })
        self.user_id = 0
        self.nickname = "Anonymous"

    def _get_cookies_as_header_dict(self) -> dict:
        """
        Extract cookies from session jar and return as dictionary for parameter wrapping.
        Also appends requestId.
        """
        cookies_dict = self.session.cookies.get_dict()
        # AddrequestId as found in j.java
        req_id = f"{int(time.time() * 1000)}_{random.randint(1000, 9999)}"
        cookies_dict["requestId"] = req_id
        return cookies_dict

    def request(self, eapi_path: str, params: dict) -> dict:
        """
        Perform a signed EAPI request to NetEase Wearable Gateway.
        Args:
            eapi_path (str): Endpoint starting with /eapi/
            params (dict): Plain dictionary of parameters
        """
        # Ensure we use api/ path for signing
        api_path = eapi_path.replace("/eapi/", "/api/")
        
        # Structure payload
        payload = params.copy()
        payload["header"] = self._get_cookies_as_header_dict()
        payload["e_r"] = "true"  # Encrypted response request flag
        
        json_payload_str = json.dumps(payload, separators=(',', ':'))
        encrypted_params = eapi_encrypt(api_path, json_payload_str)
        
        url = f"{self.base_url}{eapi_path}"
        
        try:
            # POST request with 'params' in form data
            response = self.session.post(url, data={"params": encrypted_params})
            if response.status_code != 200:
                return {"code": response.status_code, "message": f"HTTP Error {response.status_code}"}
            
            # Decrypt the binary response
            decrypted_str = eapi_decrypt(response.content)
            try:
                return json.loads(decrypted_str)
            except json.JSONDecodeError:
                return {"code": -1, "message": "Failed to parse decrypted JSON response", "raw": decrypted_str}
                
        except Exception as e:
            return {"code": -1, "message": f"Network or execution error: {str(e)}"}

    def anonymous_login(self) -> dict:
        """
        Perform anonymous device login to retrieve user credentials & cookies.
        """
        # Endpoint: /api/login/anon/device
        res = self.request("/eapi/login/anon/device", {})
        if res.get("code") == 200 and "data" in res:
            data = res["data"]
            self.user_id = data.get("userId", 0)
            self.nickname = "Guest_" + str(self.user_id)
        return res

    def send_captcha(self, phone: str, country_code: str = "86") -> dict:
        """
        Request cellphone verification captcha.
        """
        # Endpoint: /api/sms/captcha/sent
        params = {
            "cellphone": phone,
            "ctcode": country_code,
            "secrete": "music_middleuser_andrwarelogin"
        }
        return self.request("/eapi/sms/captcha/sent", params)

    def cellphone_login(self, phone: str, captcha: str, country_code: str = "86") -> dict:
        """
        Login via cellphone verification code.
        """
        # Endpoint: /api/login/cellphone
        params = {
            "phone": phone,
            "captcha": captcha,
            "countrycode": country_code
        }
        res = self.request("/eapi/login/cellphone", params)
        if res.get("code") == 200:
            # NetEase login returns account metadata
            account = res.get("account", {})
            profile = res.get("profile", {})
            self.user_id = account.get("id", profile.get("userId", 0))
            self.nickname = profile.get("nickname", "User_" + str(self.user_id))
        return res

    def like_song(self, song_id: int, like: bool = True) -> dict:
        """
        Add (or remove) song to red heart favorites playlist.
        Required to trigger the free listening trialMode=86 scene.
        """
        # Endpoint: /api/song/like
        params = {
            "like": "true" if like else "false",
            "trackId": str(song_id),
            "userid": str(self.user_id)
        }
        return self.request("/eapi/song/like", params)

    def get_play_url(self, song_id: int, use_bypass: bool = True) -> dict:
        """
        Retrieve playback URL for a song.
        Args:
            song_id (int): The song identifier
            use_bypass (bool): If True, activates standard Red Heart watch trialScene (86)
        """
        # Endpoint: /api/song/enhance/player/url/v1
        params = {
            "ids": f'["{song_id}_0"]',
            "level": "standard",
            "encodeType": "aac"
        }
        if use_bypass:
            params["trialMode"] = "86"  # PLAY_LIST_STAR watch trial bypass
        else:
            params["trialMode"] = "0"
            
        return self.request("/eapi/song/enhance/player/url/v1", params)


def main():
    parser = argparse.ArgumentParser(description="NetEase Watch Free Listening Client")
    parser.add_argument("--song", type=int, help="Target song ID to fetch play URL")
    parser.add_argument("--phone", type=str, help="Phone number for cellphone login (optional)")
    parser.add_argument("--captcha", type=str, help="Captcha verification code (if logging in via phone)")
    parser.add_argument("--send-code", action="store_true", help="Send captcha verification code and exit")
    parser.add_argument("--no-like", action="store_true", help="Do not auto-like the song before play (may fail if not in favorites)")
    parser.add_argument("--unlike-after", action="store_true", help="Remove the song from favorites after fetching play URL")
    
    args = parser.parse_args()
    
    api = NeteaseWearableAPI()
    
    # 1. Handle Login state
    if args.phone:
        if args.send_code:
            print(f"[*] Sending captcha code to {args.phone}...")
            res = api.send_captcha(args.phone)
            print("[Response]:", json.dumps(res, indent=2, ensure_ascii=False))
            sys.exit(0)
        elif args.captcha:
            print(f"[*] Logging in via phone {args.phone}...")
            res = api.cellphone_login(args.phone, args.captcha)
            if res.get("code") != 200:
                print(f"[!] Login failed: {res.get('message', 'Unknown error')}")
                sys.exit(1)
            print(f"[+] Login Succeeded! Welcome {api.nickname} (UID: {api.user_id})")
        else:
            print("[!] Phone login requires both --phone and --captcha, or --send-code")
            sys.exit(1)
    else:
        # Default to Guest/Anonymous Login
        print("[*] Initiating Anonymous Device Login...")
        res = api.anonymous_login()
        if res.get("code") != 200:
            print(f"[!] Guest Login failed: {res.get('message', 'Unknown error')}")
            sys.exit(1)
        print(f"[+] Guest Login Succeeded! Temp UID: {api.user_id}")

    # Interactive flow if no song ID is provided
    song_id = args.song
    if not song_id:
        try:
            val = input("[?] Enter song ID to fetch: ").strip()
            if not val:
                print("[!] No song ID provided. Exiting.")
                sys.exit(1)
            song_id = int(val)
        except KeyboardInterrupt:
            print("\nExiting.")
            sys.exit(0)
        except ValueError:
            print("[!] Invalid song ID.")
            sys.exit(1)

    # 2. Add song to favorites (Liking the song)
    if not args.no_like:
        print(f"[*] Adding song {song_id} to favorites to satisfy PLAY_LIST_STAR condition...")
        like_res = api.like_song(song_id, api.user_id, like=True)
        if like_res.get("code") == 200:
            print("[+] Successfully added to red heart favorites!")
        else:
            print(f"[!] Warning: Failed to like song: {like_res.get('message', 'Unknown error')}. Bypassing anyway...")

    # 3. Retrieve playback URL with trialMode=86
    print(f"[*] Retrieving play URL with trialMode=86 for song {song_id}...")
    url_res = api.get_play_url(song_id, use_bypass=True)
    
    # 4. Clean up favorites if requested
    if args.unlike_after and not args.no_like:
        print(f"[*] Cleaning up: Removing song {song_id} from favorites...")
        api.like_song(song_id, api.user_id, like=False)

    # 5. Output results
    print("\n" + "=" * 50)
    print("REVERSE-ENGINEERING RESULTS:")
    print("=" * 50)
    if url_res.get("code") == 200 and "data" in url_res and len(url_res["data"]) > 0:
        track_info = url_res["data"][0]
        play_url = track_info.get("url")
        if play_url:
            print(f"TRACK ID:   {track_info.get('id')}")
            print(f"PLAY URL:   {play_url}")
            print(f"SIZE:       {track_info.get('size')} bytes")
            print(f"BR:         {track_info.get('br')} kbps")
            print(f"TYPE:       {track_info.get('type')}")
            print(f"FREETRIAL:  {track_info.get('freeTrialInfo')}")
        else:
            print("[!] No URL found in the response. Server might have rejected the trial scene.")
            print("[Server Response Payload]:", json.dumps(url_res, indent=2, ensure_ascii=False))
    else:
        print("[!] Failed to fetch play URL.")
        print("[Server Response Payload]:", json.dumps(url_res, indent=2, ensure_ascii=False))
    print("=" * 50 + "\n")


if __name__ == "__main__":
    main()
