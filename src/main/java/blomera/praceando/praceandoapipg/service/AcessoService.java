/*
 * Class: AcessoService
 * Description: Service for the Acesso entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 01/10/2024
 * Last Updated: 01/10/2024
 */

package blomera.praceando.praceandoapipg.service;

import blomera.praceando.praceandoapipg.model.Acesso;
import blomera.praceando.praceandoapipg.repository.AcessoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AcessoService {

    private final AcessoRepository acessoRepository;

    public AcessoService(AcessoRepository acessoRepository) {
        this.acessoRepository = acessoRepository;
    }

    /**
     * @return uma lista de objetos Acesso se existirem, ou null se não houver nenhum acesso.
     */
    public List<Acesso> getAcessos() {
        List<Acesso> acessos = acessoRepository.findAll();
        return acessos.isEmpty() ? null : acessos;
    }

    /**
     * @return acesso pelo id, se ele existir, caso contrário, retorna null
     */
    public Acesso getAcessoById(Long id) {
        Optional<Acesso> acesso = acessoRepository.findById(id);
        return acesso.orElse(null);
    }

    /**
     * @return acesso deletado.
     */
    public Acesso deleteAcessoById(Long id) {
        Acesso acesso = getAcessoById(id);
        if (acesso != null) acessoRepository.deleteById(id);
        return acesso;
    }

    /**
     * @return acesso inserido.
     */
    public Acesso saveAcesso(Acesso acesso) {
        acesso.setDtAtualizacao(LocalDateTime.now());
        return acessoRepository.save(acesso);
    }

    /**
     * Atualiza os dados de um acesso.
     * @param id Id do acesso a ser atualizado.
     * @param acesso Acesso com os novos dados.
     * @return acesso atualizado ou nulo, caso o acesso não exista
     */
    public Acesso updateAcesso(Long id, Acesso acesso) {
        Acesso existingAcesso = getAcessoById(id);
        if (existingAcesso != null) {
            existingAcesso.setNmAcesso(acesso.getNmAcesso());
            existingAcesso.setDtAtualizacao(LocalDateTime.now());
            return acessoRepository.save(existingAcesso);
        }
        return null;
    }
}
