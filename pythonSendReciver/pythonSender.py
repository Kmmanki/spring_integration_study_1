import socket, time
from threading import Thread

host="localhost"
port=10001

#Scoket Open
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.connect((host, port))

def recvFromServer():
    global sock
    while True:
        data = sock.recv(100)
        msg = data.decode() # 읽은 데이터 디코딩
        print('recv msg:', msg)

t1 = Thread(target=recvFromServer)
t1.start()

while True:
    msg = input("MSG: ")
    # print(msg + )
    sock.sendall(bytes(msg +'\r\n', 'utf-8'))
    

sock.close()
