/*
 * Class: UsuarioRepository
 * Description: Repository for the Usuario entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 09/09/2024
 * Last Updated: 30/09/2024
 */
package blomera.praceando.praceandoapipg.repository;

import blomera.praceando.praceandoapipg.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
        @Override
        @Query(value = "SELECT u FROM Usuario u WHERE u.dtDesativacao IS NULL", nativeQuery = true)
        List<Usuario> findAll();
}
