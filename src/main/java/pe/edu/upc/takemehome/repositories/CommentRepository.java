package pe.edu.upc.takemehome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.takemehome.entities.Comment;
import pe.edu.upc.takemehome.entities.Order;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
