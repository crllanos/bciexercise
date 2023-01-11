package cl.bci.bciexercise.controller;

import cl.bci.bciexercise.dto.UserDTO;
import cl.bci.bciexercise.dto.UserResponseDTO;
import cl.bci.bciexercise.service.UserService;
import cl.bci.bciexercise.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final Util util;

    /**
     * OK Ese endpoint deberá recibir un usuario con los campos "nombre", "correo", "contraseña", "teléfono"
     * OK Responder el código de status HTTP adecuado
     *
     * OK En caso de éxito, retorne el usuario y los siguientes campos:
     * OK ○ id: id del usuario (UUID)
     * OK ○ created: fecha de creación del usuario
     * OK ○ modified: fecha de la última actualización de usuario
     * OK ○ last_login: del último ingreso (en caso de nuevo usuario, va a coincidir con fecha de creación)
     * OK ○ token: token de acceso de la API (puede ser UUID o JWT)
     * OK ○ isactive: Indica si el usuario sigue habilitado dentro del sistema.
     * OK Si caso el correo conste en la base de datos, deberá retornar un error "El correo ya registrado".
     * OK El correo debe seguir una expresión regular para validar que formato sea el correcto.
     * OK La clave debe seguir una expresión regular para validar que formato sea el correcto.
     * OK El valor de la expresión regular debe ser configurable
     * OK El token deberá ser persistido junto con el usuario
     *
     * OK Banco de datos en memoria. Ejemplo: HSQLDB o H2.
     * Readme explicando cómo probarlo.
     * Diagrama de la solución.
     * JWT como token
     * Pruebas unitarias
     * Swagger
     *
     */


    @PostMapping
    public UserResponseDTO createUser(@RequestBody UserDTO user){

        log.info("POST /api/v1/user request: {}", util.objToJson(user));
        UserResponseDTO response = userService.createUser(user);
        log.info("POST /api/v1/user response: {}", util.objToJson(response));

        return response;
    }
}
