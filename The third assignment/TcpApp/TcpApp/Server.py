from socket import *

Server = ""
s_port = 11451
Buffsize = 1919810
ADDR = (Server, s_port)

serverSock = socket(AF_INET, SOCK_STREAM)
serverSock.bind(ADDR)
serverSock.listen(8)


def main():
    while True:
        print("噔 噔 咚...")
        tcpSock, addr = serverSock.accept()
        print("...来自", addr)
        while True:
            conversation = tcpSock.recv(Buffsize).decode()
            if conversation == "exit":
                break
            tcpSock.send("response from Server!".encode())
    tcpSock.close()

if __name__ == '__main__':
    main()