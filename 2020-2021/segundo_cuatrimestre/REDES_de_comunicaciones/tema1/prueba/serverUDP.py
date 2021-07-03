from socket import *
serverPort = 12000
serverSocket = socket(AF_INET, SOCK_DGRAM)
serverSocket.bind(('', serverPort))
print("Servidor preparado para recibir")
while 1:
    message, clientAddress = serverSocket.recvfrom(2048)
    modifedMessage = message.upper()
    serverSocket.sendto(modifedMessage, clientAddress)
