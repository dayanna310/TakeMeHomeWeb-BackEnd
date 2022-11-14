package pe.edu.upc.takemehome.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "notifications")
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    private String title;
    private String description;
    @Column(length = 2000)
    private String urlImage;
    private Date date;
    private Boolean viewed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Notification(String title, String description, String urlImage, Date date, User user) {
        this.description=description;
        this.urlImage=urlImage;
        this.date=date;
        this.user=user;
        this.viewed=false;
        this.title=title;
    }


}
