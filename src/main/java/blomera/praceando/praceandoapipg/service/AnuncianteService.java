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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnuncianteService {

    private final AnuncianteRepository anuncianteRepository;

    public AnuncianteService(AnuncianteRepository anuncianteRepository) {
        this.anuncianteRepository = anuncianteRepository;
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
            existingAnunciante.setNmEmpresa(anunciante.getNmEmpresa());
            existingAnunciante.setNrCnpj(anunciante.getNrCnpj());
            existingAnunciante.setNrTelefone(anunciante.getNrTelefone());
            existingAnunciante.setDtNascimento(anunciante.getDtNascimento());
            return anuncianteRepository.save(existingAnunciante);
        }
        return null;
    }
}
