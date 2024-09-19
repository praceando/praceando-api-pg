/*
 * Class: GeneroService
 * Description: Service for the Genero entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 16/09/2024
 * Last Updated: 16/09/2024
 */

package blomera.praceando.praceandoapipg.service;

import blomera.praceando.praceandoapipg.model.Genero;
import blomera.praceando.praceandoapipg.repository.GeneroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeneroService {

    private final GeneroRepository generoRepository;

    public GeneroService(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    /**
     * @return uma lista de objetos Genero se existirem, ou null se não houver nenhum gênero.
     */
    public List<Genero> getGeneros() {
        List<Genero> generos = generoRepository.findAll();
        return generos.isEmpty() ? null : generos;
    }

    /**
     * @return gênero pelo id, se ele existir, caso contrário, retorna null
     */
    public Genero getGeneroById(Long id) {
        Optional<Genero> genero = generoRepository.findById(id);
        return genero.orElse(null);
    }

    /**
     * @return gênero deletado.
     */
    public Genero deleteGeneroById(Long id) {
        Genero genero = getGeneroById(id);
        if (genero != null) generoRepository.deleteById(id);
        return genero;
    }

    /**
     * @return gênero inserido.
     */
    public Genero saveGenero(Genero genero) {
        return generoRepository.save(genero);
    }

    /**
     * Atualiza os dados de um gênero.
     * @param id Id do gênero a ser atualizado.
     * @param genero Gênero com os novos dados.
     * @return gênero atualizado ou nulo, caso o gênero não exista
     */
    public Genero updateGenero(Long id, Genero genero) {
        Genero existingGenero = getGeneroById(id);
        if (existingGenero != null) {
            existingGenero.setDsNome(genero.getDsNome());
            existingGenero.setDtAtualizacao(genero.getDtAtualizacao());
            return generoRepository.save(existingGenero);
        }
        return null;
    }
}
