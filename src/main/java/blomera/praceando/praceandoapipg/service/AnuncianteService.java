/*
 * Class: AnuncianteService
 * Description: Service for the Anunciante entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 09/09/2024
 * Last Updated: 09/09/2024
 */

package blomera.praceando.praceandoapipg.service;

import blomera.praceando.praceandoapipg.model.Anunciante;
import blomera.praceando.praceandoapipg.repository.AnuncianteRepository;
import blomera.praceando.praceandoapipg.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AnuncianteService {

    private final AnuncianteRepository anuncianteRepository;
    private final UsuarioRepository usuarioRepository;
    private final JdbcTemplate jdbcTemplate;

    public AnuncianteService(AnuncianteRepository anuncianteRepository, UsuarioRepository usuarioRepository, JdbcTemplate jdbcTemplate) {
        this.anuncianteRepository = anuncianteRepository;
        this.usuarioRepository = usuarioRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @return uma lista de objetos Anunciante se existirem, ou null se não houver nenhum anunciante.
     */
    public List<Anunciante> getAnunciantes() {
        List<Anunciante> anunciantes = anuncianteRepository.findAll();
        return anunciantes.isEmpty() ? null : anunciantes;
    }

    /**
     * @return anunciante pelo id, se ele existir, caso contrário, retorna null
     */
    public Anunciante getAnuncianteById(Long id) {
        Optional<Anunciante> anunciante = anuncianteRepository.findById(id);
        return anunciante.orElse(null);
    }

    /**
     * @return anunciante deletado.
     */
    public Anunciante deleteAnuncianteById(Long id) {
        Anunciante anunciante = getAnuncianteById(id);
        if (anunciante != null) anuncianteRepository.deleteById(id);
        return anunciante;
    }

    /**
     * @return anunciante inserido.
     */
    public Anunciante saveAnunciante(Anunciante anunciante) {
        anunciante.setDsSenha(new BCryptPasswordEncoder().encode(anunciante.getDsSenha()));
        anunciante.setDtCriacao(LocalDateTime.now());
        anunciante.setDtAtualizacao(LocalDateTime.now());

        return anuncianteRepository.save(anunciante);
    }

    /**
     * Atualiza os dados de um anunciante.
     * @param id Id do anunciante a ser atualizado.
     * @param anunciante Anunciante com os novos dados.
     * @return anunciante atualizado ou nulo, caso a compra não exista
     */
    public Anunciante updateAnunciante(Long id, Anunciante anunciante) {
        Anunciante existingAnunciante = getAnuncianteById(id);
        if (existingAnunciante != null) {
            existingAnunciante.setAcesso(anunciante.getAcesso());
            existingAnunciante.setCdInventarioAvatar(anunciante.getCdInventarioAvatar());
            existingAnunciante.setGenero(anunciante.getGenero());
            existingAnunciante.setCdTipoUsuario(anunciante.getCdTipoUsuario());
            existingAnunciante.setNmUsuario(anunciante.getNmUsuario());
            existingAnunciante.setDsEmail(anunciante.getDsEmail());
            existingAnunciante.setDsSenha(new BCryptPasswordEncoder().encode(anunciante.getDsSenha()));
            existingAnunciante.setIsPremium(anunciante.getIsPremium());
            existingAnunciante.setDsUsuario(anunciante.getDsUsuario());
            existingAnunciante.setDtCriacao(LocalDateTime.now());
            existingAnunciante.setDtAtualizacao(LocalDateTime.now());
            existingAnunciante.setDtNascimento(anunciante.getDtNascimento());
            existingAnunciante.setNmEmpresa(anunciante.getNmEmpresa());
            existingAnunciante.setNrCnpj(anunciante.getNrCnpj());
            existingAnunciante.setNrTelefone(anunciante.getNrTelefone());
            return anuncianteRepository.save(existingAnunciante);
        }
        return null;
    }

    public int calculateAge(LocalDate dtNascimento) {
        return jdbcTemplate.execute((ConnectionCallback<Integer>) con -> {
            try (CallableStatement callableStatement = con.prepareCall("{ ? = CALL FNC_CALCULAR_IDADE(?) }")) {
                callableStatement.registerOutParameter(1, Types.INTEGER);
                callableStatement.setDate(2, java.sql.Date.valueOf(dtNascimento));

                callableStatement.execute();

                return callableStatement.getInt(1);
            }
        });
    }

    @Transactional
    public Anunciante validateAndPersistAnunciante(Anunciante anunciante) {
        int idade = calculateAge(anunciante.getDtNascimento());
        if (idade < 18) {
            throw new IllegalArgumentException("A idade do anunciante deve ser maior ou igual a 18 anos.");
        }

        return saveAnunciante(anunciante);
    }
}
