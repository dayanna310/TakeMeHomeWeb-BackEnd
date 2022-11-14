package pe.edu.upc.takemehome.entities;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="supports")
@NoArgsConstructor
public class Support {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Date date;


    public Support (String comment, User user, Date date){
        this.comment = comment;
        this.user = user;
        this.date = date;
    }
}