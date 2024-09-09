/*
 * Class: UsuarioRepository
 * Description: Repository for the Usuario entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 09/09/2024
 * Last Updated: 09/09/2024
 */
package blomera.praceando.praceandoapipg.repository;

import blomera.praceando.praceandoapipg.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
