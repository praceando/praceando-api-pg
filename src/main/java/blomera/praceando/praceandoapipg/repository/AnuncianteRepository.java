/*
 * Class: AnuncianteRepository
 * Description: Repository for the Anunciante entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 05/09/2024
 * Last Updated: 05/09/2024
 */

package blomera.praceando.praceandoapipg.repository;

import blomera.praceando.praceandoapipg.model.Anunciante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnuncianteRepository extends JpaRepository<Anunciante, Long> {

}
