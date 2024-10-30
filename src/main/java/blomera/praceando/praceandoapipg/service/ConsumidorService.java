/*
 * Class: ConsumidorService
 * Description: Service for the Consumidor entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 16/09/2024
 * Last Updated: 16/09/2024
 */

package blomera.praceando.praceandoapipg.service;

import blomera.praceando.praceandoapipg.model.Anunciante;
import blomera.praceando.praceandoapipg.model.Consumidor;
import blomera.praceando.praceandoapipg.model.Usuario;
import blomera.praceando.praceandoapipg.repository.ConsumidorRepository;
import blomera.praceando.praceandoapipg.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ConsumidorService {

    private final ConsumidorRepository consumidorRepository;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ConsumidorService(ConsumidorRepository consumidorRepository, UsuarioRepository usuarioRepository) {
        this.consumidorRepository = consumidorRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * @return uma lista de objetos Consumidor se existirem, ou null se não houver nenhum consumidor.
     */
    public List<Consumidor> getConsumidores() {
        List<Consumidor> consumidores = consumidorRepository.findAll();
        return consumidores.isEmpty() ? null : consumidores;
    }

    /**
     * @return consumidor pelo id, se ele existir, caso contrário, retorna null
     */
    public Consumidor getConsumidorById(Long id) {
        Optional<Consumidor> consumidor = consumidorRepository.findById(id);
        return consumidor.orElse(null);
    }

    /**
     * @return consumidor deletado.
     */
    public Consumidor deleteConsumidorById(Long id) {
        Consumidor consumidor = getConsumidorById(id);
        if (consumidor != null) consumidorRepository.deleteById(id);
        return consumidor;
    }

    /**
     * @return consumidor inserido.
     */
    public Consumidor saveConsumidor(Consumidor consumidor) {
        consumidor.setIsPremium(false);
        consumidor.setDsSenha(new BCryptPasswordEncoder().encode(consumidor.getDsSenha()));
        consumidor.setDtCriacao(LocalDateTime.now());
        consumidor.setDtAtualizacao(LocalDateTime.now());

        return consumidorRepository.save(consumidor);
    }

    /**
     * Atualiza os dados de um consumidor.
     * @param id Id do consumidor a ser atualizado.
     * @param consumidor Consumidor com os novos dados.
     * @return consumidor atualizado ou nulo, caso o concusmidor não exista
     */
    public Consumidor updateConsumidor(Long id, Consumidor consumidor) {
        Consumidor existingConsumidor = getConsumidorById(id);
        if (existingConsumidor != null) {
            existingConsumidor.setAcesso(consumidor.getAcesso());
            existingConsumidor.setCdInventarioAvatar(consumidor.getCdInventarioAvatar());
            existingConsumidor.setGenero(consumidor.getGenero());
            existingConsumidor.setNmUsuario(consumidor.getNmUsuario());
            existingConsumidor.setDsEmail(consumidor.getDsEmail());
            existingConsumidor.setDsSenha(new BCryptPasswordEncoder().encode(consumidor.getDsSenha()));
            existingConsumidor.setIsPremium(consumidor.getIsPremium());
            existingConsumidor.setDsUsuario(consumidor.getDsUsuario());
            existingConsumidor.setDtCriacao(LocalDateTime.now());
            existingConsumidor.setDtNascimento(consumidor.getDtNascimento());
            existingConsumidor.setNmNickname(consumidor.getNmNickname());
            existingConsumidor.setNrPolen(consumidor.getNrPolen());
            existingConsumidor.setDtAtualizacao(LocalDateTime.now());
            existingConsumidor.setIsPossivelAnunciar(consumidor.getIsPossivelAnunciar());

            return consumidorRepository.save(existingConsumidor);
        }
        return null;
    }

    /**
     * @return boolean representando a existência ou não do nickname
     */
    public boolean existsByNickname(String nickname) {
        return consumidorRepository.existsByNmNickname(nickname);
    }
}
