package pe.edu.upc.takemehome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.takemehome.entities.Order;

public interface OrderRepository  extends JpaRepository<Order,Long> {
}
