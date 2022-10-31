package pe.edu.upc.takemehome;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import pe.edu.upc.takemehome.entities.Order;
//import pe.edu.upc.takemehome.entities.User;
import pe.edu.upc.takemehome.repositories.OrderRepository;
import pe.edu.upc.takemehome.repositories.UserRepository;
import pe.edu.upc.takemehome.repositories.ShipmentRepository;

//import java.text.SimpleDateFormat;

@SpringBootApplication
public class TakemehomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TakemehomeApplication.class, args);
	}
	@Bean
	public CommandLineRunner mappingDemo(
			UserRepository userRepository,
			OrderRepository orderRepository,
			ShipmentRepository shipmentRepository
	){
		return args ->{



		};
	}
}
