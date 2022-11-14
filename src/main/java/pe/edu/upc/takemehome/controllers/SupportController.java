package pe.edu.upc.takemehome.controllers;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.takemehome.entities.Support;
import pe.edu.upc.takemehome.exceptions.ResourceNotFoundException;
import pe.edu.upc.takemehome.repositories.SupportRepository;
import pe.edu.upc.takemehome.repositories.UserRepository;

import pe.edu.upc.takemehome.entities.User;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
@Data
public class SupportController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SupportRepository supportRepository;

    @GetMapping("/support")
    public ResponseEntity<List<Support>> getAllSupport() {
        List<Support> supports = supportRepository.findAll();

        for (Support s : supports) {
            s.getUser().setOrders(null);
            s.getUser().setShipments(null);
            s.getUser().setCommentsSend(null);
            s.getUser().setCommentsReceived(null);
            s.getUser().setCommentsSupport(null);
            s.getUser().setNotifications(null);
        }
        return new ResponseEntity<List<Support>>(supports, HttpStatus.OK);
    }
    @PostMapping("/support/{id}")
    public ResponseEntity<Support> createSupport(@RequestBody Support support, @PathVariable("id") Long id){
        User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Not found Order with id="+id));
        long actual = System.currentTimeMillis();
        Date date= new Date(actual);
        Support newSupport = supportRepository.save(new Support(
                support.getComment(),
                user,
                date
        ));
        user.setOrders(null);
        user.setShipments(null);
        user.setCommentsReceived(null);
        user.setCommentsSend(null);
        user.setCommentsSupport(null);
        user.setNotifications(null);

        return new ResponseEntity<Support>(newSupport, HttpStatus.CREATED);
    }

}

