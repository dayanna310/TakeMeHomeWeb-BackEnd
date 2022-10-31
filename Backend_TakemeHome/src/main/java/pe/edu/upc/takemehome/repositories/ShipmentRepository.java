package pe.edu.upc.takemehome.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.takemehome.entities.Shipment;
//import pe.edu.upc.takemehome.entities.Order;

public interface ShipmentRepository extends JpaRepository<Shipment,Long>{
    //Shipment FindByOrder(Order order);
}
