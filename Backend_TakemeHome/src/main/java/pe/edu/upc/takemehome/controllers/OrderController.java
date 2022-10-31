package pe.edu.upc.takemehome.controllers;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.takemehome.entities.Order;
import pe.edu.upc.takemehome.entities.Shipment;

import pe.edu.upc.takemehome.entities.User;
import pe.edu.upc.takemehome.exceptions.ResourceNotFoundException;
import pe.edu.upc.takemehome.repositories.OrderRepository;
import pe.edu.upc.takemehome.repositories.ShipmentRepository;
import pe.edu.upc.takemehome.repositories.UserRepository;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
@Data
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        for (Order u : orders) {
            u.setUser(null);
            u.setShipment(null);
        }
        return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
    }

    @GetMapping("/orders/users/filter/{id}")
    public ResponseEntity<List<Order>> getAllAvaliableOrders(@PathVariable("id") Long id) {


        List<Order> orders = orderRepository.findAll();
        List<Order> filteredOrders= new ArrayList<Order>();


        for (Order o : orders) {
            if(o.getShipment()==null){
                if(o.getUser().getId()!=id) {
                    o.getUser().setOrders(null);
                    o.getUser().setShipments(null);
                    o.setShipment(null);
                    filteredOrders.add(o);
                }
            }
        }

        return new ResponseEntity<List<Order>>(filteredOrders, HttpStatus.OK);
    }
    @GetMapping("/orders/users/{id}/shipments")
    public ResponseEntity<List<Order>> getUserByIdWithOrderFilterShipments(@PathVariable("id") Long id){

        User user=userRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("Not found Owner with id="+id));
        List<Order> filteredOrders= new ArrayList<Order>();
        for (Order o : user.getOrders()) {
            if(o.getShipment()!=null){
                o.getUser().setOrders(null);
                o.getUser().setShipments(null);
                o.setShipment(null);
                filteredOrders.add(o);
            }
        }
        user.setOrders(null);
        return new ResponseEntity<List<Order>>(filteredOrders,HttpStatus.OK);
    }
    @GetMapping("/orders/users/{id}/noShipments")
    public ResponseEntity<List<Order>> getUserByIdWithOrderFilterNoShipments(@PathVariable("id") Long id){

        User user=userRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("Not found Owner with id="+id));
        List<Order> filteredOrders= new ArrayList<Order>();
        for (Order o : user.getOrders()) {
            if(o.getShipment()==null){
                o.getUser().setOrders(null);
                o.getUser().setShipments(null);
                o.setShipment(null);
                filteredOrders.add(o);
            }
        }
        user.setOrders(null);
        return new ResponseEntity<List<Order>>(filteredOrders,HttpStatus.OK);
    }

    @GetMapping("/orders/users")
    public ResponseEntity<List<Order>> getAllOrdersWithUser() {
        List<Order> orders = orderRepository.findAll();

        for (Order o : orders) {
            o.getUser().setOrders(null);
            o.getUser().setShipments(null);
            o.setShipment(null);
        }

        return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
    }
    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderByIdWithUser(@PathVariable("id") Long id){

        Order order=orderRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Not found Order with id="+id));

        order.getUser().setOrders(null);
        order.getUser().setShipments(null);
        order.setShipment(null);
        return new ResponseEntity<Order>(order,HttpStatus.OK);
    }
    @GetMapping("/orders/shipments")
    public ResponseEntity<List<Order>> getAllOrdersWithShipments() {
        List<Order> orders = orderRepository.findAll();

        for (Order o : orders) {
            o.getShipment().setUser(null);
            o.getShipment().setOrder(null);
            o.setUser(null);
        }

        return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
    }

    @GetMapping("/orders/shipments/user")
    public ResponseEntity<List<Order>> getAllOrdersWithShipmentsAndUser() {
        List<Order> orders = orderRepository.findAll();
        for (Order o : orders) {
            o.getShipment().setUser(null);
            o.getShipment().setOrder(null);
            o.getUser().setOrders(null);
            o.getUser().setShipments(null);
        }
        return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
    }


    @PostMapping("/orders/{ac}")
    public ResponseEntity<Order> createOrder(@RequestBody Order order, @PathVariable("ac") Long id) {
        User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Not found Order with id="+id));
        Order newOrder =  orderRepository.save(new Order(
                order.getLimitDate(),
                order.getDestinationCountry(),
                order.getDestinationCity(),
                order.getDestinationAddress(),
                order.getOriginCountry(),
                order.getOriginCity(),
                order.getUrlProduct(),
                order.getNameProduct(),
                order.getCategoryProduct(),
                order.getPriceProduct(),
                order.getDimensionsProduct(),

                user));
        user.setOrders(null);
        user.setShipments(null);

        return new ResponseEntity<Order>(newOrder, HttpStatus.CREATED);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<HttpStatus> deleteOrderById(@PathVariable("id") Long id){
        Order orderDelete=orderRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Not found Order with id="+id));
        if(orderDelete.getShipment()!=null) {
            shipmentRepository.deleteById(orderDelete.getShipment().getId());
            orderRepository.deleteById(id);
        }
        else{
            orderRepository.deleteById(id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
