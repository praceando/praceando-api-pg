/*
 * Class: GeneroRepository
 * Description: Repository for the Genero entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 06/09/2024
 * Last Updated: 06/09/2024
 */
package blomera.praceando.praceandoapipg.repository;

import blomera.praceando.praceandoapipg.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneroRepository extends JpaRepository<Genero, Long> {
}
