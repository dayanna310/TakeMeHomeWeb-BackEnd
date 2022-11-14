package pe.edu.upc.takemehome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.takemehome.entities.Notification;
import pe.edu.upc.takemehome.entities.User;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {


    List<Notification> findAllByOrderByDateDesc();
}
