"""
 Fichero que contiene la implementacion de funciones auxiliares como la de cifrado, firmado y descifrado.
 Autores:
    Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
    Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es
 Grupo 2301, Doble Grado.
"""
from Crypto.Signature import pkcs1_15
from Crypto.Cipher import AES, PKCS1_OAEP
from Crypto.Random import get_random_bytes
from Crypto.PublicKey import RSA
from Crypto.Hash import SHA256
from Crypto.Util.Padding import pad, unpad
import json
import requests

# se guarda la parte del path común en todas las peticiones
root_url = 'https://vega.ii.uam.es:8080/api'

# Guarda el token del usuario.
with open('../keys/token.txt') as file:
    token = file.readline().strip('\n')

'''
********
* FUNCIÓN: generar_fichero(mensaje, nombre_fichero)
* ARGS_IN: mesaje           - contenido a guardar en el fichero.
*          nombre_fichero   - nombre del fichero en el que se va a guarda el mensaje.
* DESCRIPCIÓN: Guarda en el fichero el mensaje.
* ARGS_OUT: No devuelve nada.
********'''
def generar_fichero(mensaje, nombre_fichero):
    with open(nombre_fichero, "wb") as file:
        file.write(mensaje)


'''
********
* FUNCIÓN: get_public_key(dest_id)
* ARGS_IN: dest_id           - contenido a guardar en el fichero.
* DESCRIPCIÓN: Busca la clave publica del usuario con NIA dest_id.
* ARGS_OUT: La clave publica buscada.
********'''
def get_public_key(dest_id):
    print('Recuperando clave publica de ' + str(dest_id))

    # El NIA debe de tener longitud 6.
    if len(str(dest_id)) != 6:
        print('Error get_public_key, NIA no valido')
        exit()

    # se obtiene el path para la petición.
    path = root_url + '/users/getPublicKey'
    headers = {'Content-Type': 'application/json', 'Content-Length': str(6),
               'Authorization': 'Bearer ' + token}

    # se manda la petición y se obtiene la respuesta
    response = requests.post(path, headers=headers, data=json.dumps({'userID': dest_id}))
    if response.status_code != 200:
        print('No se pudo buscar la clave publica, error: ' + str(response.status_code))
        exit()

    response = response.json()
    public_key = response['publicKey']
    public_key = RSA.import_key(public_key)

    return public_key


'''
********
* FUNCIÓN: cifra_fichero(file_name, dest_id)
* ARGS_IN: file_name           - Nombre del fichero a cifrar.
*          dest_id             - NIA del usuario destino. Si es None usar la clave publica local.
* DESCRIPCIÓN: Cifra el fichero file_name y devuelve el nombre del fichero cifrado.
* ARGS_OUT: El nombre del fichero cifrado.
********'''
def cifrar_fichero(file_name, dest_id):
    print('Cifrando fichero...')
    # El NIA debe de tener longitud 6.
    if len(str(dest_id)) != 6:
        print('Error cifrar_fichero, ID no valido')
        exit()

    # Clave publica del destinatario.
    public_key = get_public_key(dest_id)

    file_name = "../files/" + file_name

    # leemos el contenido del fichero pasado por argumento, y los ciframos.
    with open(file_name, "rb") as file:
        contenido = file.read()

    # Clave simetrica de 32 Bytes = 256 bits.
    key = get_random_bytes(32)

    # Cifrado simetrico.
    cipher = AES.new(key, AES.MODE_CBC)
    mensaje_cifrado = cipher.encrypt(pad(contenido, AES.block_size))
    iv = cipher.iv

    # Cifrado asimetrico.
    cipher_rsa = PKCS1_OAEP.new(public_key)
    enc_key = cipher_rsa.encrypt(key)

    contenido_cifrado = iv + enc_key + mensaje_cifrado

    # se guarda en un fichero.
    file_name = file_name[:file_name.index('.', 2)] + '_cifrado.txt'
    generar_fichero(contenido_cifrado, file_name)
    return file_name


'''
********
* FUNCIÓN: firmar_fichero(file_name)
* ARGS_IN: file_name           - Nombre del fichero a firmar.
* DESCRIPCIÓN: Firma el fichero file_name.
* ARGS_OUT: Devuelve el nombre del fichero firmado.
********'''
def firmar_fichero(file_name):
    print('Firmando fichero...')
    # Se firma con la clave privada.
    with open("../keys/private.pem", "rb") as file:
        private_key = file.read()
    private_key = RSA.import_key(private_key)

    file_name = "../files/" + file_name

    # leemos el contenido del fichero a firmar, y los firmamos.
    with open(file_name, "rb") as file:
        contenido = file.read()
    h = SHA256.new(contenido)
    contenido_firmado = pkcs1_15.new(private_key).sign(h)

    # se guarda en un fichero.
    nombre = file_name[:file_name.index('.', 2)] + '_firmado.txt'
    generar_fichero(contenido_firmado + contenido, nombre)
    return nombre


'''
********
* FUNCIÓN: descifrar_mensaje(contenido)
* ARGS_IN: contenido           - bytes cifrados que se van a descifrar.
* DESCRIPCIÓN: Descifra el contenido y lo retorna.
* ARGS_OUT: Devuelve el contenido descifrado.
********'''
def descifrar_mensaje(contenido):
    print('Descifrando fichero...')
    with open("../keys/private.pem", "rb") as file:
        private_key = file.read()
        private_key = RSA.import_key(private_key)

    iv = contenido[:16]
    key_ciphered = contenido[16:272]
    body_ciphered = contenido[272:]

    # Se descifra la clave simetrica con tu clave privada.
    cipher_rsa = PKCS1_OAEP.new(private_key)
    key = cipher_rsa.decrypt(key_ciphered)

    # Se usa la clave simetrica y el IV.
    cipher = AES.new(key, AES.MODE_CBC, iv)
    mensaje_descifrado = cipher.decrypt(body_ciphered)
    # Se le quita el padding.
    mensaje_descifrado = unpad(mensaje_descifrado, AES.block_size)

    return mensaje_descifrado
