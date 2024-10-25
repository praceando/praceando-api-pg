/*
 * Class: UsuarioRepository
 * Description: Repository for the Usuario entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 09/09/2024
 * Last Updated: 02/10/2024
 */
package blomera.praceando.praceandoapipg.repository;

import blomera.praceando.praceandoapipg.model.Evento;
import blomera.praceando.praceandoapipg.model.Produto;
import blomera.praceando.praceandoapipg.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
        @Override
        @Query(value = "SELECT * FROM usuario u WHERE u.dt_desativacao IS NULL", nativeQuery = true)
        List<Usuario> findAll();

        Optional<Usuario> findUsuarioByIdAndDtDesativacaoIsNull(Long id);
        Optional<Usuario> findUsuarioByDsEmailEqualsIgnoreCaseAndDtDesativacaoIsNull(String email);

        Optional<Usuario> findByDsEmailEqualsIgnoreCase(String email);

        boolean existsByDsEmail(String dsEmail);

}
