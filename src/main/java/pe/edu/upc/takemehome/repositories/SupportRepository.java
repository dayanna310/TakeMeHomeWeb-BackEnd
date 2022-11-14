package pe.edu.upc.takemehome.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.takemehome.entities.Support;

public interface SupportRepository extends JpaRepository<Support,Long> {
}
