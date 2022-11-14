package pe.edu.upc.takemehome.controllers;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.takemehome.entities.Category;
import pe.edu.upc.takemehome.entities.Comment;
import pe.edu.upc.takemehome.entities.Order;
import pe.edu.upc.takemehome.entities.User;
import pe.edu.upc.takemehome.exceptions.ResourceNotFoundException;
import pe.edu.upc.takemehome.repositories.CommentRepository;
import pe.edu.upc.takemehome.repositories.OrderRepository;
import pe.edu.upc.takemehome.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
@Data
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> getAllComment() {
        List<Comment> comments = commentRepository.findAll();
        for (Comment c : comments) {
            c.getUserReceives().setOrders(null);
            c.getUserReceives().setShipments(null);
            c.getUserReceives().setCommentsReceived(null);
            c.getUserReceives().setCommentsSend(null);
            c.getUserReceives().setNotifications(null);
            c.getUserSend().setOrders(null);
            c.getUserSend().setShipments(null);
            c.getUserSend().setCommentsReceived(null);
            c.getUserSend().setCommentsSend(null);
            c.getUserSend().setNotifications(null);
            c.getUserSend().setCommentsSupport(null);
        }
        return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
    }
    @PostMapping("/comments")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {

        Comment newComment =  commentRepository.save(new Comment(
                comment.getUserSend(),comment.getUserReceives(), comment.getTitle(), comment.getCategory(), comment.getContent(), comment.getDate()
        ));
        newComment.setUserReceives(null);
        newComment.setUserSend(null);
        return new ResponseEntity<Comment>(newComment, HttpStatus.CREATED);
    }
    @GetMapping("/comments/{id}/positive")
    public ResponseEntity<List<Comment>> getAllCommentPositive(@PathVariable("id") Long id) {
        List<Comment> comments = commentRepository.findAll();
        List<Comment> commentsFilter = new ArrayList<Comment>();

        for (Comment c : comments) {
            if(c.getUserReceives().getId()==id) {
                if(c.getCategory()== Category.Comment){
                c.getUserReceives().setOrders(null);
                c.getUserReceives().setShipments(null);
                c.getUserReceives().setCommentsReceived(null);
                c.getUserReceives().setCommentsSend(null);
                c.getUserReceives().setNotifications(null);
                c.getUserReceives().setCommentsSupport(null);
                c.getUserSend().setOrders(null);
                c.getUserSend().setShipments(null);
                c.getUserSend().setCommentsReceived(null);
                c.getUserSend().setCommentsSend(null);
                c.getUserSend().setNotifications(null);
                c.getUserSend().setCommentsSupport(null);
                commentsFilter.add(c);
                }

            }
        }
        return new ResponseEntity<List<Comment>>(commentsFilter, HttpStatus.OK);
    }
    @GetMapping("/comments/{id}/negative")
    public ResponseEntity<List<Comment>> getAllCommentNegative(@PathVariable("id") Long id) {
        List<Comment> comments = commentRepository.findAll();
        List<Comment> commentsFilter = new ArrayList<Comment>();

        for (Comment c : comments) {
            if(c.getUserReceives().getId()==id) {
                if(c.getCategory()==Category.Claim){
                    c.getUserReceives().setOrders(null);
                    c.getUserReceives().setShipments(null);
                    c.getUserReceives().setCommentsReceived(null);
                    c.getUserReceives().setCommentsSend(null);
                    c.getUserReceives().setNotifications(null);
                    c.getUserReceives().setCommentsSupport(null);
                    c.getUserSend().setOrders(null);
                    c.getUserSend().setShipments(null);
                    c.getUserSend().setCommentsReceived(null);
                    c.getUserSend().setCommentsSend(null);
                    c.getUserSend().setNotifications(null);
                    c.getUserSend().setCommentsSupport(null);
                    commentsFilter.add(c);
                }
            }
        }
        return new ResponseEntity<List<Comment>>(commentsFilter, HttpStatus.OK);
    }
}
