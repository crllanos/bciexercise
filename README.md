# Ejercicio BCI

### Genera un nuevo usuario
Error por clave poco segura:


        
        curl --location --request POST 'http://localhost:8080/api/v1/user' \
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


