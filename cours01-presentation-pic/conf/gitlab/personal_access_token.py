#!/usr/bin/python3
"""
Script that creates Personal Access Token for Gitlab API;
Tested with GitLab Community Edition 10.1.4
"""
import sys
import requests
from urllib.parse import urljoin
from bs4 import BeautifulSoup

def find_csrf_token(text):
    soup = BeautifulSoup(text, "lxml")
    token = soup.find(attrs={"name": "csrf-token"})
    param = soup.find(attrs={"name": "csrf-param"})
    data = {param.get("content"): token.get("content")}
    return data


def obtain_csrf_token(endpoint):
    r = requests.get(urljoin(endpoint, "/"))
    token = find_csrf_token(r.text)
    return token, r.cookies


def sign_in(csrf, cookies, endpoint, login, password):
    data = {
        "user[login]": login,
        "user[password]": password,
        "user[remember_me]": 0,
        "utf8": "1"
    }
    data.update(csrf)
    r = requests.post(urljoin(endpoint, "/users/sign_in"), data=data, cookies=cookies)
    token = find_csrf_token(r.text)
    return token, r.history[0].cookies


def obtain_personal_access_token(name, expires_at, csrf, cookies, endpoint):
    data = {
        "personal_access_token[expires_at]": expires_at,
        "personal_access_token[name]": name,
        "personal_access_token[scopes][]": "api",
        "utf8": "1"
    }
    data.update(csrf)
    r = requests.post(urljoin(endpoint, "/profile/personal_access_tokens"), data=data, cookies=cookies)
    soup = BeautifulSoup(r.text, "lxml")
    token = soup.find('input', id='created-personal-access-token').get('value')
    return token


def main():
    name = sys.argv[1]
    endpoint = sys.argv[2]
    login = sys.argv[3]
    password = sys.argv[4]
    expires_at = 'none'
    csrf1, cookies1 = obtain_csrf_token(endpoint)
    csrf2, cookies2 = sign_in(csrf1, cookies1, endpoint, login, password)
    token = obtain_personal_access_token(name, expires_at, csrf2, cookies2, endpoint)
    print(token)


if __name__ == "__main__":
    main()
