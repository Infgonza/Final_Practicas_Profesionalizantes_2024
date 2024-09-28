package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entities.Domicilio;

@Repository
public interface DomicilioRepository extends JpaRepository<Domicilio, Long> {

}
