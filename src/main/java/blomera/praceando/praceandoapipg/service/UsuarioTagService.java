/*
 * Class: UsuarioTagService
 * Description: Service for the UsuarioTag entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 30/10/2024
 * Last Updated: 30/10/2024
 */
package blomera.praceando.praceandoapipg.service;

import blomera.praceando.praceandoapipg.model.Evento;
import blomera.praceando.praceandoapipg.repository.AcessoRepository;
import blomera.praceando.praceandoapipg.repository.UsuarioTagRepository;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class UsuarioTagService {
    private final UsuarioTagRepository usuarioTagRepository;
    private final JdbcTemplate jdbcTemplate;

    public UsuarioTagService(UsuarioTagRepository usuarioTagRepository, JdbcTemplate jdbcTemplate) {
        this.usuarioTagRepository = usuarioTagRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Método para inserir as tags de um usuário.
     */
    public void saveUsuarioTag(Integer idConsumidor, Integer idEvento, List<String> tags) {
        try {
            java.sql.Array tagsArray;
            if (tags != null) {
                tagsArray = jdbcTemplate.getDataSource().getConnection().createArrayOf("VARCHAR", tags.toArray());
            } else {
                tagsArray = null;
            }

            jdbcTemplate.execute((ConnectionCallback<Void>) con -> {
                    try (CallableStatement callableStatement = con.prepareCall("CALL PRC_INSERIR_USUARIO_TAG(?, ?, ?)")) {
                    callableStatement.setInt(1, idConsumidor);

                    if (idEvento != null) {
                        callableStatement.setInt(2, idEvento);
                    } else {
                        callableStatement.setNull(2, java.sql.Types.INTEGER);
                    }

                    if (tagsArray != null) {
                        callableStatement.setArray(3, tagsArray);
                    } else {
                        callableStatement.setNull(3, java.sql.Types.ARRAY);
                    }

                    callableStatement.execute();
                    return null;
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar tags do usuário: " + e.getMessage(), e);
        }
    }

}
