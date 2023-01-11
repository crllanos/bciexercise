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
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    private final Util util;

    @PostMapping
    public UserResponseDTO createUser(@RequestBody UserDTO user){

        log.info("POST /api/v1/user request: {}", util.objToJson(user));
        UserResponseDTO response = userService.createUser(user);
        log.info("POST /api/v1/user response: {}", util.objToJson(response));

        return response;
    }
}
