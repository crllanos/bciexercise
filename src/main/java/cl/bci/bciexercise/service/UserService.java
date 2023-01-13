package cl.bci.bciexercise.service;

import cl.bci.bciexercise.dto.UserDTO;
import cl.bci.bciexercise.dto.UserResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserResponseDTO createUser(UserDTO user);

    void saveUser(UserDTO user);


}
