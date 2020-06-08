## Colectivo
Empieza unicamente con el representante
Estructura:
- Nombre del colectivo (unico)
- Representante del colectivo
- Lista de ciudadanos que pertencen directamente
- Lista de subcolectivos

## Administrador
Puede:
- Tiene un usuario y contrasena que no se pueden cambiar
- Elige el numero de votos minimos necesarios para que un proyecto se pueda enviar a financiacion
- Autoriza o rechaza la voluntad de registrarse de un ciudadano 
- Autoriza o rechaza la creacion de proyecto (obligatoriamente) (antes de que se empieze a votar)
    - Si lo rechaza tiene que dar un motivo max 50 caracteres. Este motivo se muestra al que 
      ha propuesto el proyecto
    - Estos proyectos se tienen que eliminar de la aplicacion.
- Puede bloquear y desbloquear a ciudadanos registrados. 
    - Al estar bloqueados, sus apoyos se ignoran (todos los que tenga). También los que haya apoyado como colectivo

## Ciudadano
Hace login con (nombre o NIF) y contrasena
Estructura:
- nombre (unico)
- NIF (unico) (9 caracteres)
- Contrasena
Puede:
- Registrarse si aun no esta registrado en el sistema (Se piden los datos)
- Iniciar sesion con nif y contrasena
- Un usuario puede crear un colectivo nuevo (vacio) y este tendra como representante al creador.
- Apuntarse a un colectivo. Tiene que tener una lista de todos los colectivos en la que elegir. 
- Darse de baja de un colectivo de un colectivo a no ser que sea un representante
- Proponer un proyecto (si es representante también en nombre del colectivo)
    - Automaticamente queda subscrito (si es como colectivo, solo el representante)
- Apoyar un proyecto (si es representante también en nombre del colectivo)
    - No puede haber apoyos duplicados
    - No se pueden quitar apoyos
- Subscribirse a un proyecto (solo a nivel individual aunque la propuesta sea de un colectivo)
    - Debe de recibir notificaciones cuando:
        - El proyecto se manda a financiacion
        - Se obtiene el resultado del proceso de financiacion.
    - Al apoyar NO se subscribe automaticamente
    - Al crear  SI se subscribe automaticamente 
- Consultar los proyectos propuestos (informe de popularidad)
    - Solo el que ha pedido el proyecto (si es un colectivo solo el representante)
    - Tiene que indicar el numero de apoyos que tiene el colectivo
- Consultar los colectivos a los que se pertenece
    - Los colectivos a los que un ciudadano pertenece son aquellos a los que te has apuntado y aquellos de los que eres representante
    - Consultar el informe de afinidad. Solo se puede pedir sobre los colectivos a los que se pertenece. Para dos colectivos seleccionados (sean C1, C2 los colectivos):
        
        <img src="https://render.githubusercontent.com/render/math?math=\text{Afinidad}=\frac{\text{proyectos de C1 apoyados por C2} %2B \text{proyectos de C2 apoyados por C1}}{\text{proyectos de C1} %2B \text{proyectos de C2}}">
    
### Representante de colectivo
Puede:
- Crear sub-colectivos que estaran vacios inicialmente. Solo tendran al representante
- Propone proyectos (automaticamente todos los de su colectivo lo apoyan) y pide apoyo para los proyectos

## Proyecto
Lo pueden proponer los ciudadanos individualmente o como representantes de un colectivo. Automaticamente el que lo propone lo apoya
Tiene status Disponible si tiene numero minimo de votos y caducado si han pasado 30 dias sin recibir apoyos.
Al caducar ya no puede recibir votos. Pero si ya tenia bastantes votos, aun se puede enviar

Estructura:
- Titulo (maximo 25)
- Descripcion (maximo 500)
- Importe monetario pedido
- Importe monetario adjudicado
- Tipo
    - Infraestructura
        - Una foto (obligatoria)
        - Conjunto de distritos afectados (hay 10 en total, se indican al crear) 
    - Social
        - Grupo social (maximo 50)
        - Es nacional o internacional
- Fecha de creacion
- Identificador unico

## Financiacion
- Es un sistema externo.
- Un proyecto se puede enviar cuando tiene los apoyos minimos. El que lo ha propuesto es el unico que lo puede enviar. 
- La respuesta posible de ese sistema externo es:
    - Pendiente: no se ha decidido todavia
    - Aceptado con un determinado importe
    - Rechazado
- Si el proyecto esta aceptado o rechazado, no se aceptan mas votos. Si esta en pendiente, se puede seguir votando pero esos votos no valen nada
- Mientras esta pendiente puede caducar pero luego puede aceptarse o rechazarse.


## Requisitos
- No hay limites en cantidad de usuarios, proyectos o colectivos
