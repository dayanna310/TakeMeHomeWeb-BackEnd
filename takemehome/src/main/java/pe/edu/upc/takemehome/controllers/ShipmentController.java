package pe.edu.upc.takemehome.controllers;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.takemehome.entities.Order;
import pe.edu.upc.takemehome.entities.User;
import pe.edu.upc.takemehome.entities.Shipment;
import pe.edu.upc.takemehome.entities.State;
import pe.edu.upc.takemehome.exceptions.ResourceNotFoundException;
import pe.edu.upc.takemehome.repositories.OrderRepository;
import pe.edu.upc.takemehome.repositories.UserRepository;
import pe.edu.upc.takemehome.repositories.ShipmentRepository;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
@Data
public class ShipmentController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @GetMapping("/shipments")
    public ResponseEntity<List<Shipment>> getAllShipments() {
        List<Shipment> shipments = shipmentRepository.findAll();

        for (Shipment u : shipments) {
            u.setUser(null);
            u.setOrder(null);
        }
        return new ResponseEntity<List<Shipment>>(shipments, HttpStatus.OK);
    }

    @GetMapping("/shipments/users")
    public ResponseEntity<List<Shipment>> getAllShipmentsWithUser() {
        List<Shipment> shipments = shipmentRepository.findAll();

        for (Shipment s : shipments) {
            s.getUser().setShipments(null);
            s.getUser().setOrders(null);
            s.setOrder(null);
        }

        return new ResponseEntity<List<Shipment>>(shipments, HttpStatus.OK);
    }
    @GetMapping("/shipments/users/{id}")
    public ResponseEntity<List<Shipment>> getAllShipmentsWithIdUser(@PathVariable("id") Long userid) {
        List<Shipment> shipments = shipmentRepository.findAll();
        List<Shipment> filteredShipment= new ArrayList<Shipment>();
        for (Shipment s : shipments) {
            if(s.getUser().getId()==userid){
                s.getUser().setShipments(null);
                s.getUser().setOrders(null);
                s.getOrder().setShipment(null);
                s.getOrder().getUser().setOrders(null);
                s.getOrder().getUser().setShipments(null);
                filteredShipment.add(s);

            }
        }

        return new ResponseEntity<List<Shipment>>(filteredShipment, HttpStatus.OK);
    }
    @GetMapping("/shipments/orders")
    public ResponseEntity<List<Shipment>> getAllShipmentsWithOrders() {
        List<Shipment> shipments = shipmentRepository.findAll();

        for (Shipment s : shipments) {
            s.getOrder().setUser(null);
            s.getOrder().setShipment(null);
            s.setUser(null);
        }

        return new ResponseEntity<List<Shipment>>(shipments, HttpStatus.OK);
    }

    @GetMapping("/shipments/orders/user")
    public ResponseEntity<List<Shipment>> getAllShipmentWithOrdersAndUser() {
        List<Shipment> shipments = shipmentRepository.findAll();
        for (Shipment o : shipments) {
            o.getOrder().setUser(null);
            o.getOrder().setShipment(null);
            o.getUser().setOrders(null);
            o.getUser().setShipments(null);
        }
        return new ResponseEntity<List<Shipment>>(shipments, HttpStatus.OK);
    }


    @PostMapping("/shipments/{userid}/orders/{oid}")
    public ResponseEntity<Shipment> createShipment(@RequestBody Shipment shipment,  @PathVariable("userid") Long userid, @PathVariable("oid") Long orderid){
        User user=userRepository.findById(userid).orElseThrow(()->new ResourceNotFoundException("Not found User with id="+userid));
        Order order= orderRepository.findById(orderid).orElseThrow(()->new ResourceNotFoundException("Not found Order with id="+orderid));
        //Shipment nShip = shipmentRepository.FindByOrder(nShip.getOrder());
        //if (nShip == null) {
        if(order.getShipment() ==null) {
            Shipment newShipment = shipmentRepository.save(new Shipment(shipment.getState(),
                    shipment.getPayment(),
                    shipment.getPaymentdate(),
                    shipment.getArrivalDate(),
                    order,
                    user
            ));

            newShipment.setPaymentdate(null);
            newShipment.setOrder(null);
            newShipment.setUser(null);
            return new ResponseEntity<Shipment>(newShipment, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<Shipment>(order.getShipment(), HttpStatus.OK);
        }

    }

    @DeleteMapping("/shipments/{id}")
    public ResponseEntity<HttpStatus> deleteShipmentById(@PathVariable("id") Long id){
        shipmentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
