package pe.edu.upc.takemehome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.takemehome.entities.User;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsernameAndPassword(String username,String password);
    User findByUsername(String username);

    User findByIdAndPassword(Long id,String password);

}