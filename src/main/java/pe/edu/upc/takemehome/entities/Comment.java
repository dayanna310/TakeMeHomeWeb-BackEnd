package pe.edu.upc.takemehome.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="comments")
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_send_id")
    private User userSend;

    @ManyToOne
    @JoinColumn(name = "user_receives_id")
    private User userReceives;
    private String title;
    private Category category;
    private String content;

    public Comment(User userSend, User userReceives, String title, Category category, String content, Date date) {
        this.userSend = userSend;
        this.userReceives = userReceives;
        this.title = title;
        this.category = category;
        this.content = content;
        this.date = date;
    }

    private Date date;
}
