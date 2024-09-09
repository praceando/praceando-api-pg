/*
 * Class: PagamentoRepository
 * Description: Repository for the Local entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 09/09/2024
 * Last Updated: 09/09/2024
 */
package blomera.praceando.praceandoapipg.repository;

import blomera.praceando.praceandoapipg.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
