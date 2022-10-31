package pe.edu.upc.takemehome.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
//import java.awt.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String email;

    private String phone;
    private Date birthday;
    private String country;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Shipment> shipments;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    public User(String name, String username, String email,String phone, Date birthday, String country, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone=phone;
        this.birthday = birthday;
        this.country = country;
        this.password = password;
    }
}
