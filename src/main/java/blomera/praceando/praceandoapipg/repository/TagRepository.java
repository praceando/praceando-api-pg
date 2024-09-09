/*
 * Class: ProdutoRepository
 * Description: Repository for the Produto entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 29/08/2024
 * Last Updated: 29/08/2024
 */
package blomera.praceando.praceandoapipg.repository;

import blomera.praceando.praceandoapipg.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
