# Ejercicio BCI
Esta API persiste usuarios validando previamente algunos valores:
* El email debe también ser un correo válido.
* El email no debe estar previamente registrado.
* La contraseña usada debe ser segura, cumpliendo los siguientes requisitos:
  *  Entre 8 y 20 catacteres.
  *  Contener a lo menos 1 digito.
  * Uso de mayúsculas y minúsculas.
  *  Contener uno de los siguientes caracteres: ! @ # & ( )


Para generar un nuevo usuario, primero es necesario autenticarse con las siguientes credenciales:
* email: admin@bci.cl
* password: admIn@321

La API está protegida mediante JWT. Para autenticarse y poder generar usuarios, primero es necesario usar el endpoint de inicio de sesión, con las credenciales proporcionadas y recuperar el token de acceso que debe usarse en el header Authorization como Bearer token.

Cualquier usuario creado en el sistema podrá logearse y recibir un JWT que lo acredita como autenticado dentro del sistema, pero solamente el usuario descrito tiene el rol de ADMIN y por lo tanto es el unico autorizado para crear nuevos usuarios en la base de datos.

## Login admin
        curl --location --request POST 'http://localhost:8080/api/login' \
        --header 'Content-Type: application/x-www-form-urlencoded' \
        --data-urlencode 'email=admin@bci.cl' \
        --data-urlencode 'password=admIn@321'

## Genera un nuevo usuario
### Caso 1: Error por clave poco segura:

        curl --location --request POST 'http://localhost:8080/api/v1/user' \
        --header 'Authorization: Bearer <TOKEN_JWT>' \
        --header 'Content-Type: application/json' \
        --data-raw '{
        "name": "Juan Rodriguez",
        "email": "juan@rodriguez.org",
        "password": "x@123",
        "phones": [
        {
        "number": "1234567",
        "citycode": "1",
        "contrycode": "57"
        }
        ]
        }'

### Caso 2: Correo no válido:

        curl --location --request POST 'http://localhost:8080/api/v1/user' \
        --header 'Authorization: Bearer <TOKEN_JWT>' \
        --header 'Content-Type: application/json' \
        --data-raw '{
        "name": "Juan Rodriguez",
        "email": "juan_rodriguez.org",
        "password": "AAAbbbCCC@123",
        "phones": [
        {
        "number": "1234567",
        "citycode": "1",
        "contrycode": "57"
        }
        ]
        }'


### Caso 3: Usuario creado exitosamente:

        curl --location --request POST 'http://localhost:8080/api/v1/user' \
        --header 'Authorization: Bearer <TOKEN_JWT>' \
        --header 'Content-Type: application/json' \
        --data-raw '{
        "name": "Juan Rodriguez",
        "email": "juan@rodriguez2.org",
        "password": "AAAbbbCCC@123",
        "phones": [
        {
        "number": "11111111",
        "citycode": "1",
        "contrycode": "56"
        },
        {
        "number": "11111111",
        "citycode": "2",
        "contrycode": "56"
        }
        ]
        }'

Response esperado

        {
        "id": "dc396239-32ed-40d0-bf41-c5adb65115c9",
        "name": "Juan Rodriguez",
        "email": "juan@rodriguez.org",
        "phones": [
        {
        "number": "11111111",
        "citycode": "1",
        "contrycode": "56"
        },
        {
        "number": "11111111",
        "citycode": "2",
        "contrycode": "56"
        }
        ],
        "created": "2023-01-13",
        "modified": "2023-01-13",
        "token": "e291dfa5-af7b-4e2d-b5be-694514a0bf1b",
        "isActive": true,
        "last_login": "2023-01-13T11:50:42.706178"
        }