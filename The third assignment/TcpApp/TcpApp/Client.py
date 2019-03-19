from socket import *

Server = "localhost"
s_port = 11451
Buffsize = 1919810
ADDR = (Server, s_port)

clientSock = socket(AF_INET, SOCK_STREAM)
clientSock.connect(ADDR)

while True:
    message = input("输入信息 :")
    if message == "exit":
        break
    clientSock.send(message.encode())
    response = clientSock.recv(Buffsize)
    if not response:
        break
    print(response.decode("utf-8"))
clientSock.close()