/*
 * Class: InteresseService
 * Description: Service for the Interesse entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 17/09/2024
 * Last Updated: 17/09/2024
 */

package blomera.praceando.praceandoapipg.service;

import blomera.praceando.praceandoapipg.model.Interesse;
import blomera.praceando.praceandoapipg.repository.InteresseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InteresseService {

    private final InteresseRepository interesseRepository;

    public InteresseService(InteresseRepository interesseRepository) {
        this.interesseRepository = interesseRepository;
    }

    /**
     * @return uma lista de objetos Interesse se existirem, ou null se não houver nenhum interesse.
     */
    public List<Interesse> getInteresses() {
        List<Interesse> interesses = interesseRepository.findAll();
        return interesses.isEmpty() ? null : interesses;
    }

    /**
     * @return interesse pelo id, se ele existir, caso contrário, retorna null
     */
    public Interesse getInteresseById(Long id) {
        Optional<Interesse> interesse = interesseRepository.findById(id);
        return interesse.orElse(null);
    }

    /**
     * @return interesse deletado.
     */
    public Interesse deleteInteresseById(Long id) {
        Interesse interesse = getInteresseById(id);
        if (interesse != null) interesseRepository.deleteById(id);
        return interesse;
    }

    /**
     * @return interesse inserido.
     */
    public Interesse saveInteresse(Interesse interesse) {
        return interesseRepository.save(interesse);
    }

    /**
     * Atualiza os dados de um interesse.
     * @param id Id do interesse a ser atualizado.
     * @param interesse Interesse com os novos dados.
     * @return interesse atualizado ou nulo, caso o interesse não exista
     */
    public Interesse updateInteresse(Long id, Interesse interesse) {
        Interesse existingInteresse = getInteresseById(id);
        if (existingInteresse != null) {
            existingInteresse.setDtAtualizacao(interesse.getDtAtualizacao());
            existingInteresse.setConsumidor(interesse.getConsumidor());
            existingInteresse.setEvento(interesse.getEvento());
            return interesseRepository.save(existingInteresse);
        }
        return null;
    }
}
