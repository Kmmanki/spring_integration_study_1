import socket, time
from telnetlib import Telnet

host="localhost"
port=10001

#Scoket Open
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.connect((host, port))
# sock.send()
while True:
    msg = input("MSG: ")
    # print(msg + )
    sock.sendall(bytes(msg +'\r\n', 'utf-8'))
    

sock.close()
