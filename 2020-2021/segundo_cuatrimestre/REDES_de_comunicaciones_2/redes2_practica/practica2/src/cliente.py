"""
 Fichero que contiene el manejo de los argumentos que manda el cliente.
 Autores:
    Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
    Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es
 Grupo 2301, Doble Grado.
"""
from libreria_secure_box import *
from os import remove
import argparse
import requests


# se crea un argparse para parsear los argumentos.
parserCipher = argparse.ArgumentParser(description='Cipher')

parserCipher.add_argument('--encrypt', type=str,
                          help='cifra el fichero localmente.')

parserCipher.add_argument('--sign', type=str,
                          help='firma el fichero localmente.')

parserCipher.add_argument('--enc_sign', type=str,
                          help='firma y cifra el fichero localmente.')

parserCipher.add_argument('--create_id', nargs='+', type=str,
                          help='Registra un usuario con: nombre y email, y como opcional un alias.')

parserCipher.add_argument('--search_id', type=str,
                          help='buscar un usuario o fichero que contenga la cadena elegida.')

parserCipher.add_argument('--delete_id', type=int,
                          help='borra un usuario del servidor con el id pasado por argumento.')

parserCipher.add_argument('--list_files', action='store_true',
                          help='muestra los ids de los ficheros.')

parserCipher.add_argument('--upload', type=str,
                          help='sube un fichero al id pasado por argumento.')

parserCipher.add_argument('--download', type=str,
                          help='descarga un fichero del id pasado por argumento.')

parserCipher.add_argument('--delete_file', type=str,
                          help='borra un fichero con el id pasado por argumento.')

parserCipher.add_argument('--source_id', type=int,
                          help='indica el ID del usuario emisor.')

parserCipher.add_argument('--dest_id', type=int,
                          help='indica el ID del usuario receptor.')

args = parserCipher.parse_args()


# Si se trata de cifrado y firma de ficheros locales.
if args.encrypt is not None and args.dest_id is not None:
    print('Encriptando fichero ' + args.encrypt + '...')

    file_name = cifrar_fichero(args.encrypt, args.dest_id)
    print('Fichero ' + file_name + ' cifrado.')

elif args.sign is not None:
    print('Firmando fichero ' + args.sign + '...')

    file_name = firmar_fichero(args.sign)
    print('Fichero ' + file_name + ' firmado.')

elif args.enc_sign is not None and args.dest_id is not None:
    print("Firmando y luego encriptando " + args.enc_sign + ' ...')

    file_name = firmar_fichero(args.enc_sign)

    file_name = file_name[file_name.rindex('/')+1:]
    file_name = cifrar_fichero(file_name, args.dest_id)

    print('Fichero ' + file_name + ' cifrado y firmado.')

elif args.search_id is not None:
    # en este caso, se quiere buscar a alguien que contenga la cadena pasada por argumento.
    print('Buscando usuario ' + args.search_id + ' en el servidor...')

    # se obtiene el path para la petición
    path = root_url+'/users/search'
    headers = {'Content-Type': 'application/json', 'Content-Length': str(len(args.search_id)),
               'Authorization': 'Bearer ' + token}

    # se manda la petición y se obtiene la respuesta
    response = requests.post(path, headers=headers, data=json.dumps({'data_search': args.search_id}))

    if response.status_code != 200:
        print('No se pudo buscar el id, error: ' + str(response.status_code))
        exit()

    response = response.json()

    print(str(len(response)) + ' usuarios encontrados:')
    cont = 0
    for element in response:
        cont += 1
        print('[' + str(cont) + '] ' + str(element['nombre']) + ', '
              + str(element['email']) + ', ID: ' + str(element['userID']))

elif args.delete_id is not None:
    print('Solicitando borrado de la identidad', '#'+str(args.delete_id), '...')

    path = root_url+'/users/delete'
    headers = {'Content-Type': 'application/json', 'Content-Length': str(len(str(args.delete_id))),
               'Authorization': 'Bearer ' + token}

    # mandamos la petición y obtenemos la respuesta. Solo puedes borrar tu id, sino error 401.
    response = requests.post(path, headers=headers, data=json.dumps({'userID': args.delete_id}))
    if response.status_code != 200:
        print('No se pudo borrar el id, error: ' + str(response.status_code))
        exit()

    response = response.json()

    # obtenemos de la respuesta el userID, que será el que ha borrado.
    user_id = response['userID']

    print('Identidad con', 'ID#' + str(user_id) + ' borrada correctamente.')

elif args.create_id is not None:
    # en este caso se quiere crear una nueva identidad, con nombre y email, y con opción de un alias.
    print("Creando un id...")

    argsCreateId = args.create_id   # obtenemos una lista con los argumentos
    nargs = len(argsCreateId)       # obtenemos el número de argumentos.

    # si el número de argumentos es 2 o 3 (que es lo que se requiere)
    if 1 < nargs < 4:
        print('Generando par de claves RSA de 2048 bits...')
        private_key = RSA.generate(2048)
        public_key = private_key.publickey()

        # Se guardan la clave publica y privada.
        with open("../keys/private.pem", "wb") as file:
            file.write(private_key.export_key())


        # obtenemos el tamaño del body de la petición, así como el path y la cabecera.
        len_args = 0
        for arg in argsCreateId:
            len_args += len(arg)

        path = root_url+'/users/register'
        headers = {'Content-Type': 'application/json', 'Content-Length': str(len_args),
                   'Authorization': 'Bearer ' + token}

        # obtenemos la public key en formato PEM
        cert = (public_key.export_key()).decode()

        # vemos si el usuario que quiere crear la identidad ha indicado el alias o no.
        if nargs == 3:
            data = {'nombre': argsCreateId[0], 'email': argsCreateId[1], 'alias': argsCreateId[2], 'publicKey': cert}
        else:
            data = {'nombre': argsCreateId[0], 'email': argsCreateId[1], 'publicKey': cert}

        response = requests.post(path, headers=headers, data=json.dumps(data))

        # se obtiene la respuesta, y de ésta el ID que se ha creado con éxito.
        if response.status_code != 200:
            print('No se pudo crear un id, error: ' + str(response.status_code))
            exit()

        response = response.json()
        userid = response['userID']
        print('Identidad', 'ID#' + str(userid) + ' creada correctamente.')

elif (args.upload is not None) and (args.dest_id is not None):
    print('Subiendo el fichero ' + args.upload + ' a SecureBox...')

    file_name1 = firmar_fichero(args.upload)

    file_name2 = cifrar_fichero(file_name1[9:], args.dest_id)

    # Lee el contenido firmado y cifrado.
    with open(file_name2, 'rb') as file:
        contenido = file.read()

    path = root_url + '/files/upload'
    headers = {'Content-Length': str(len(contenido)),
               'Authorization': 'Bearer ' + token}

    # Sube el fichero con la autorizacion del token.
    with open(file_name2, 'rb') as file:
        response = requests.post(path, headers=headers, files={'ufile': file})

    if response.status_code != 200:
        print('No se pudo crear un id, error: ' + str(response.status_code))
        exit()

    response = response.json()

    # Se borran archivos intermedios.
    remove(file_name1)
    remove(file_name2)
    print('Archivo subido con el ID: ' + response['file_id'])

elif args.download is not None and args.source_id is not None:
    print('Descargando el fichero del SecureBox...')

    path = root_url+'/files/download'
    headers = {'Content-Type': 'application/json', 'Content-Length': str(len(args.download)),
               'Authorization': 'Bearer ' + token}

    # Se descarga el fichero.
    response = requests.post(path, headers=headers, data=json.dumps({'file_id': args.download}))
    if response.status_code != 200:
        print('No se pudo descargar, error: ' + str(response.status_code))
        exit()
    # La respuesta no es JSON sino un fichero binario.
    contenido = response.content

    print('Fichero descargado, ' + str(len(contenido)) + ' bytes leidos.')

    mensaje_descifrado = descifrar_mensaje(contenido)

    # Se recuperan las partes del mensaje.
    hash_cifrado = mensaje_descifrado[:256]
    contenido_descifrado = mensaje_descifrado[256:]

    # Se calcula lo que deberia dar el hash y la clave publica del emisor.
    h = SHA256.new(contenido_descifrado)
    public_key = get_public_key(args.source_id)

    # Verifica que la firma es correcta.
    try:
        pkcs1_15.new(public_key).verify(h, mensaje_descifrado[:256])
        print("La firma es valida.")
    except (ValueError, TypeError):
        print("Error, la firma no es valida.")

    # Se genera un fichero.
    file_name = '../files/' + args.download + '.txt'
    generar_fichero(contenido_descifrado, file_name)
    print('Fichero ' + file_name + ' descargado.')

elif args.delete_file is not None:
    print('Solicitando borrado del archivo', '#' + str(args.delete_file), '...')

    path = root_url+'/files/delete'
    headers = {'Content-Type': 'application/json', 'Content-Length': str(len(str(args.delete_file))),
               'Authorization': 'Bearer ' + token}

    # Mandamos la petición y obtenemos la respuesta.
    response = requests.post(path, headers=headers, data=json.dumps({'file_id': args.delete_file}))
    if response.status_code != 200:
        print('No se pudo borrar el fichero, error: ' + str(response.status_code))
        exit()

    response = response.json()

    # obtenemos de la respuesta el userID, que será el que se ha borrado.
    file_id = response['file_id']

    print('Archivo con', 'ID#' + str(file_id) + ' borrado correctamente.')

elif args.list_files:
    # en este caso, se solicita una lista de los ficheros del usuario, y no requiere de argumentos.

    # se obtiene el path y la cabecera.
    path = root_url+'/files/list'
    headers = {'Content-Type': 'application/json', 'Authorization': 'Bearer ' + token}

    # se manda la petición get (porque no tiene body).
    response = requests.get(path, headers=headers)
    if response.status_code != 200:
        print('No se pudo crear un id, error: ' + str(response.status_code))
        exit()

    response = response.json()

    print(str(len(response['files_list'])) + ' ficheros encontrados:')
    cont = 0
    for element in response['files_list']:
        cont += 1
        print('[' + str(cont) + '] ID: ' + element['fileID'] + ' fileName: ' + element['fileName'])

else:
    print('Error, introduce los parametros correctos.')
