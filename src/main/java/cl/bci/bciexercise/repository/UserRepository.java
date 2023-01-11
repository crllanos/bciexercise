package cl.bci.bciexercise.repository;

import cl.bci.bciexercise.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>{

    UserEntity findUserByEmail(String email);

}
