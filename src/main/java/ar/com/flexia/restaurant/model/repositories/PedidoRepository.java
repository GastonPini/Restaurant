package ar.com.flexia.restaurant.model.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.flexia.restaurant.model.entity.*;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	public Optional<Pedido> findByNombre(String nombre);

	public Optional<Pedido> findPedidoByNombreAndMesa_Id(String nombre, Long id);
	
	public Optional<Pedido> findByIdAndMesa_Id(long pedidoId, long mesaId);

	public Optional<Pedido> findPedidoByPagadoIsFalseAndMesa_Id(Long mesaId);

}