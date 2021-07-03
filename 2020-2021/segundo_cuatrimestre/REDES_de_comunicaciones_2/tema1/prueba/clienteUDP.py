from socket import *
serverName = 'gremio'
serverPort = 12000
clientSocket = socket(AF_INET, SOCK_DGRAM)
message1 = "hola"#input ('Input lowercase sentence:')
message = bytes(message1, 'utf-8')
clientSocket.sendto(message,(serverName, serverPort))
modifedMessage, serverAddress = clientSocket.recvfrom(2048)
print(modifedMessage)
clientSocket.close()
