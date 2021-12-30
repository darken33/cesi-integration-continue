import sys
import requests

filename = sys.argv[1]
endpoint = sys.argv[2]
login = sys.argv[3]
password = sys.argv[4]

with open(filename, 'r') as fd:
    data = fd.read()
r = requests.post(endpoint + '/scriptText', auth=(login, password), data={'script': data})
