import socket, time
from threading import Thread

host="localhost"
port=10002
isClose= 'N'
from gtts import gTTS
def stopFunction():
    global isClose
    isClose = input("종료?")

#Scoket Open
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# 여러번 ip.port를 바인드하면 에러가 나므로, 이를 방지하기 위한 설정이 필요
# socket.SOL_SOCKET: 소켓 옵션
# SO_REUSEADDR 옵션은 현재 사용 중인 ip/포트번호를 재사용할 수 있다.
# 커널이 소켓을 사용하는 중에도 계속해서 사용할 수 있다
server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

# server socket에 ip와 port를 붙여줌(바인드)
server_socket.bind((host, port))

# 클라이언트 접속 준비 완료
server_socket.listen()
t1 =Thread(target=stopFunction, args=())
t1.start()
print('echo server start') # echo program: 입력한 값을 메아리치는 기능(그대로 다시 보냄)

# accept(): 클라이언트 접속 기다리며 대기
# 클라이언트가 접속하면 서버-클라이언트 1:1 통신이 가능한 작은 소켓(client_soc)을 만들어서 반환
# 접속한 클라이언트의 주소(addr) 역시 반환
client_soc, addr = server_socket.accept()

print('connected client addr:', addr)


def makeMp3(text):
    
    tts = gTTS(text=text, lang='ko')
    tts.save("helloKO.mp3")


# recv(메시지크기): 소켓에서 크기만큼 읽는 함수
# 소켓에 읽을 데이터가 없으면 생길 때까지 대기함
while isClose!='y':
    print(isClose)
    data = client_soc.recv(100)
    msg = data.decode() # 읽은 데이터 디코딩
    msg = msg.rstrip("\r\n")
    # print(bytes(msg)) 
    msg = msg + " > 3\r\n"
    # makeMp3(msg)
    print(msg)
    
    client_soc.sendall(msg.encode("utf-8")) # 에코메세지 클라이언트로 보냄

server_socket.close() # 사용했던 서버 소켓을 닫아줌
