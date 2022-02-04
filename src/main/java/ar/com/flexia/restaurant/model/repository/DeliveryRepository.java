package ar.com.flexia.restaurant.model.repository;

import ar.com.flexia.restaurant.model.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,Long> {

    public Optional<Delivery> findById(long pedidoId);
    
}