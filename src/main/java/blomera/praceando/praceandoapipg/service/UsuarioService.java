/*
 * Class: UsuarioService
 * Description: Service for the Usuario entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 18/09/2024
 * Last Updated: 02/10/2024
 */

package blomera.praceando.praceandoapipg.service;

import blomera.praceando.praceandoapipg.model.Usuario;
import blomera.praceando.praceandoapipg.repository.UsuarioRepository;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final JdbcTemplate jdbcTemplate;

    public UsuarioService(UsuarioRepository usuarioRepository, JdbcTemplate jdbcTemplate) {
        this.usuarioRepository = usuarioRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @return uma lista de objetos Usuario se existirem, ou null se não houver nenhum usuário.
     */
    public List<Usuario> getUsuarios() {
        List<Usuario> users = usuarioRepository.findAll();
        return users.isEmpty() ? null : users;
    }

    /**
     * @return usuário pelo id, se ele existir, caso contrário, retorna null
     */
    public Usuario getUsuarioById(Long id) {
        Optional<Usuario> user = usuarioRepository.findUsuarioByIdAndDtDesativacaoIsNull(id);
        return user.orElse(null);
    }

    /**
     * @return usuário pelo e-mail, se ele existir, caso contrário, retorna null
     */
    public Usuario getUsuarioByEmail(String email) {
        Optional<Usuario> user = usuarioRepository.findUsuarioByDsEmailEqualsIgnoreCaseAndDtDesativacaoIsNull(email);
        return user.orElse(null);
    }

    /**
     * @return usuário deletado.
     */
    public Usuario deleteUsuarioById(Long id) {
        Usuario user = getUsuarioById(id);
        if (user != null) usuarioRepository.deleteById(id);
        return user;
    }

    /**
     * @return usuário inserido.
     */
    public Usuario saveUsuario(Usuario user) {
        user.setNmUsuario(user.getNmUsuario().strip().toUpperCase());
        user.setDsSenha(new BCryptPasswordEncoder().encode(user.getDsSenha()));
        user.setDtAtualizacao(LocalDateTime.now());

        return usuarioRepository.save(user);
    }

    /**
     * Atualiza os dados de um usuário.
     * @param id Id do usuário a ser atualizado.
     * @param user Usuario com os novos dados.
     * @return usuário atualizado ou nulo, caso ele não exista
     */
    public Usuario updateUsuario(Long id, Usuario user) {
        Usuario existingUsuario = getUsuarioById(id);
        if (existingUsuario != null) {
            existingUsuario.setAcesso(user.getAcesso());
            existingUsuario.setCdInventarioAvatar(user.getCdInventarioAvatar());
            existingUsuario.setGenero(user.getGenero());
            existingUsuario.setNmUsuario(user.getNmUsuario());
            existingUsuario.setDsEmail(user.getDsEmail());
            existingUsuario.setDsSenha(new BCryptPasswordEncoder().encode(user.getDsSenha()));
            existingUsuario.setIsPremium(user.getIsPremium());
            existingUsuario.setDsUsuario(user.getDsUsuario());
            existingUsuario.setDtAtualizacao(LocalDateTime.now());
            return usuarioRepository.save(existingUsuario);
        }
        return null;
    }

    /**
     * @return usuário excluido.
     */
    public Optional<Usuario> softDelete(Long id) {
        Optional<Usuario> user = usuarioRepository.findById(id);
        if (user.isPresent()) {
            Usuario u = user.get();
            u.setDtDesativacao(LocalDateTime.now());
            usuarioRepository.save(u);
        }
        return user;
    }

    /**
     * @return boolean representando a existência ou não do email
     */
    public boolean existsByEmail(String dsEmail) {
        return usuarioRepository.existsByDsEmail(dsEmail);
    }

    /**
     * Atualiza o inventário do usuário com o ID do avatar fornecido,
     * utilizando uma função de banco de dados para realizar a atualização.
     *
     * @param idUsuario O ID do usuário a ser atualizado.
     * @param idAvatar O ID do avatar a ser inserido no inventário do usuário.
     */
    public void setInventory(Integer idUsuario, Integer idAvatar) {
        jdbcTemplate.execute((ConnectionCallback<Void>) con -> {
            try (CallableStatement callableStatement = con.prepareCall("{ CALL FNC_INSERIR_ID_AVATAR(?, ?) }")) {
                callableStatement.setInt(1, idUsuario);
                callableStatement.setInt(2, idAvatar);

                callableStatement.execute();

                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro adicionar inventário: " + e.getMessage(), e);
            }
        });
    }
}

