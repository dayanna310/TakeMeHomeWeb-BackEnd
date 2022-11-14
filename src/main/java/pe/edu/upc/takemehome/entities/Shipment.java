package pe.edu.upc.takemehome.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "shipments")
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;

    private State state;
    private Number payment;
    private Date paymentDate;
    private Date arrivalDate;

    public Shipment(State state,Number payment, Date paymentDate, Date arrivalDate, Order order, User user){
        this.state = state;
        this.payment = payment;
        this.paymentDate = paymentDate;
        this.arrivalDate = arrivalDate;
        this.order = order;
        this.user = user;
    }

    
    
}
