package pe.edu.upc.takemehome.controllers;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.takemehome.entities.Notification;
import pe.edu.upc.takemehome.entities.Order;
import pe.edu.upc.takemehome.entities.Shipment;
import pe.edu.upc.takemehome.entities.User;
import pe.edu.upc.takemehome.exceptions.ResourceNotFoundException;
import pe.edu.upc.takemehome.repositories.NotificationRepository;
import pe.edu.upc.takemehome.repositories.UserRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
@Data
public class NotificationController {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;



    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getAllNotifications(){

        List<Notification> notifications= notificationRepository.findAll();
        for(Notification n: notifications){
            n.getUser().setOrders(null);
            n.getUser().setShipments(null);
            n.getUser().setCommentsSend(null);
            n.getUser().setCommentsReceived(null);
            n.getUser().setCommentsSupport(null);
            n.getUser().setNotifications(null);
        }

        return new ResponseEntity<List<Notification>>(notifications, HttpStatus.OK);


    }
    @GetMapping("/notifications/one/{id}")
    public ResponseEntity<Notification> getNotification(@PathVariable("id") Long id){

        Notification n= notificationRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Not found User with id="+id));

        n.getUser().setOrders(null);
        n.getUser().setShipments(null);
        n.getUser().setCommentsSend(null);
        n.getUser().setCommentsReceived(null);
        n.getUser().setNotifications(null);
        n.getUser().setCommentsSupport(null);
        return new ResponseEntity<Notification>(n, HttpStatus.OK);


    }
    @GetMapping("/notifications/{id}")
    public ResponseEntity<List<Notification>> getNotificationsUser(@PathVariable("id") Long id){

        List<Notification> notifications= notificationRepository.findAll();
        List<Notification> filterNotifications= new ArrayList<Notification>();
        for(Notification n: notifications){
            if(n.getUser().getId()==id){
                if(n.getViewed()==false) {
                    n.getUser().setOrders(null);
                    n.getUser().setShipments(null);
                    n.getUser().setCommentsSend(null);
                    n.getUser().setCommentsReceived(null);
                    n.getUser().setNotifications(null);
                    n.getUser().setCommentsSupport(null);
                    filterNotifications.add(0,n);
                }
            }

        }

        return new ResponseEntity<List<Notification>>(filterNotifications, HttpStatus.OK);


    }
    @PutMapping("/notifications/{id}")
    public ResponseEntity<Notification> stateChange(@PathVariable("id")Long id){

        Notification notificationUpdate = notificationRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Not found User with id="+id));

        notificationUpdate.setViewed(true);

        Notification updatedNotification = notificationRepository.save(notificationUpdate);

        updatedNotification.setUser(null);


        return new ResponseEntity<Notification>(updatedNotification, HttpStatus.OK);

    }
    @PostMapping("/notifications/{id}")
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification, @PathVariable("id")Long id){

        User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Not found Order with id="+id));
        long actual = System.currentTimeMillis();
        Date date= new Date(actual);

        Notification newNotification= notificationRepository.save(new Notification(notification.getTitle(),
                notification.getDescription(), notification.getUrlImage(), date, user));

        user.setOrders(null);
        user.setShipments(null);
        user.setCommentsReceived(null);
        user.setCommentsSend(null);
        user.setNotifications(null);
        user.setCommentsSupport(null);


        return new ResponseEntity<Notification>(newNotification, HttpStatus.CREATED);

    }
}
