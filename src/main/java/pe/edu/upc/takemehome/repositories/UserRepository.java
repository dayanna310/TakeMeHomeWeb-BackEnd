package pe.edu.upc.takemehome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.takemehome.entities.User;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsernameAndPassword(String username,String password);
    User findByUsername(String username);

    User findByIdAndPassword(Long id,String password);

    @Query("SELECT  u FROM User u WHERE u.username=?1 and u.password=?2")
    User findByUsernameAndPasswordJPQL(String username, String password);

}