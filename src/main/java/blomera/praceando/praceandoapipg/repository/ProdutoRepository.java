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

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Override
    @Query(value = "SELECT p  FROM Produto p WHERE p.dtDesativacao IS NULL", nativeQuery = true)
    List<Produto> findAll();
}
