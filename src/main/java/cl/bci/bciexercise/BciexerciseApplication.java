package cl.bci.bciexercise;

import cl.bci.bciexercise.dto.UserDTO;
import cl.bci.bciexercise.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@SpringBootApplication
public class BciexerciseApplication {

	public static void main(String[] args) {
		SpringApplication.run(BciexerciseApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Script for creation of dummy admin user, in order to run the JWT integration
	 *
	 */
	 @Bean
	 CommandLineRunner run(UserService userService){
		 log.info("Creando user admin...");
	 	return args -> {
			 userService.saveUser(UserDTO.builder()
					 .name("admin")
					 .email("admin@bci.cl")
					 .password("admIn@321")
					 .role("ROLE_ADMIN")
					 .build());

		};
	 }
}
