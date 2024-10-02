/*
 * Class: UsuarioService
 * Description: Service for the Usuario entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 18/09/2024
 * Last Updated: 30/09/2024
 */

package blomera.praceando.praceandoapipg.service;

import blomera.praceando.praceandoapipg.model.Usuario;
import blomera.praceando.praceandoapipg.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * @return uma lista de objetos Usuario se existirem, ou null se não houver nenhum usuário.
     */
    public List<Usuario> getUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.isEmpty() ? null : usuarios;
    }

    /**
     * @return usuário pelo id, se ele existir, caso contrário, retorna null
     */
    public Usuario getUsuarioById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElse(null);
    }

    /**
     * @return usuário deletado.
     */
    public Usuario deleteUsuarioById(Long id) {
        Usuario usuario = getUsuarioById(id);
        if (usuario != null) usuarioRepository.deleteById(id);
        return usuario;
    }

    /**
     * @return usuário inserido.
     */
    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Atualiza os dados de um usuário.
     * @param id Id do usuário a ser atualizado.
     * @param usuario Usuario com os novos dados.
     * @return usuário atualizado ou nulo, caso ele não exista
     */
    public Usuario updateUsuario(Long id, Usuario usuario) {
        Usuario existingUsuario = getUsuarioById(id);
        if (existingUsuario != null) {
            existingUsuario.setNmUsuario(usuario.getNmUsuario());
            existingUsuario.setCdInventarioAvatar(usuario.getCdInventarioAvatar());
            existingUsuario.setGenero(usuario.getGenero());
            existingUsuario.setDsEmail(usuario.getDsEmail());
            existingUsuario.setIsPremium(usuario.getIsPremium());
            existingUsuario.setDsUsuario(usuario.getDsUsuario());
            existingUsuario.setDtAtualizacao(usuario.getDtAtualizacao());
            existingUsuario.setDtDesativacao(usuario.getDtDesativacao());
            return usuarioRepository.save(existingUsuario);
        }
        return null;
    }

    /**
     * @return usuário excluido.
     */
    public Optional<Usuario> softDelete(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            Usuario u = usuario.get();
            u.setDtDesativacao(LocalDateTime.now());
            usuarioRepository.save(u);
        }
        return usuario;
    }
}

