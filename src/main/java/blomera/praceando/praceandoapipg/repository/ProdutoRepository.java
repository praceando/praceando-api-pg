/*
 * Class: ProdutoRepository
 * Description: Repository for the Produto entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 29/08/2024
 * Last Updated: 01/10/2024
 */
package blomera.praceando.praceandoapipg.repository;

import blomera.praceando.praceandoapipg.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Override
    @Query(value = "SELECT * FROM produto p WHERE p.dt_desativacao IS NULL", nativeQuery = true)
    List<Produto> findAll();

    @Override
    @Query(value = "SELECT * FROM produto p WHERE p.dt_desativacao IS NULL AND p.id_produto = :id", nativeQuery = true)
    Optional<Produto> findById(@Param("id") Long id);

}
