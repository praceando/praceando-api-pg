/*
 * Class: LocalService
 * Description: Service for the Local entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 16/09/2024
 * Last Updated: 16/09/2024
 */

package blomera.praceando.praceandoapipg.service;

import blomera.praceando.praceandoapipg.model.Evento;
import blomera.praceando.praceandoapipg.model.Local;
import blomera.praceando.praceandoapipg.repository.LocalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LocalService {

    private final LocalRepository localRepository;

    public LocalService(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    /**
     * @return uma lista de objetos Local se existirem, ou null se não houver nenhum local.
     */
    public List<Local> getLocais() {
        List<Local> locais = localRepository.findAll();
        return locais.isEmpty() ? null : locais;
    }

    /**
     * @return local pelo id, se ele existir, caso contrário, retorna null
     */
    public Local getLocalById(Long id) {
        Optional<Local> local = localRepository.findById(id);
        return local.orElse(null);
    }

    /**
     * @return local deletado.
     */
    public Local deleteLocalById(Long id) {
        Local local = getLocalById(id);
        if (local != null) localRepository.deleteById(id);
        return local;
    }

    /**
     * @return local inserido.
     */
    public Local saveLocal(Local local) {
        local.setDtAtualizacao(LocalDateTime.now());

        return localRepository.save(local);
    }

    /**
     * Atualiza os dados de um local.
     * @param id Id do local a ser atualizado.
     * @param local Local com os novos dados.
     * @return local atualizado ou nulo, caso ele não exista
     */
    public Local updateLocal(Long id, Local local) {
        Local existingLocal = getLocalById(id);
        if (existingLocal != null) {
            existingLocal.setNmLocal(local.getNmLocal());
            existingLocal.setNrLat(local.getNrLat());
            existingLocal.setNrLong(local.getNrLong());
            existingLocal.setHrAbertura(local.getHrAbertura());
            existingLocal.setHrFechamento(local.getHrFechamento());
            existingLocal.setDtAtualizacao(LocalDateTime.now());
            return localRepository.save(existingLocal);
        }
        return null;
    }

    /**
     * @return local excluido.
     */
    public Optional<Local> softDelete(Long id) {
        Optional<Local> local = localRepository.findById(id);
        if (local.isPresent()) {
            Local l = local.get();
            l.setDtDesativacao(LocalDateTime.now());
            localRepository.save(l);
        }
        return local;
    }
}
