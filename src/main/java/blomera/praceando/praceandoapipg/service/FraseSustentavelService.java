/*
 * Class: FraseSustentavelService
 * Description: Service for the FraseSustentavel entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 16/09/2024
 * Last Updated: 16/09/2024
 */

package blomera.praceando.praceandoapipg.service;

import blomera.praceando.praceandoapipg.model.FraseSustentavel;
import blomera.praceando.praceandoapipg.repository.FraseSustentavelRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FraseSustentavelService {

    private final FraseSustentavelRepository fraseSustentavelRepository;

    public FraseSustentavelService(FraseSustentavelRepository fraseSustentavelRepository) {
        this.fraseSustentavelRepository = fraseSustentavelRepository;
    }

    /**
     * @return uma lista de objetos FraseSustentavel se existirem, ou null se não houver nenhuma fraseSustentavel.
     */
    public List<FraseSustentavel> getFraseSustentaveis() {
        List<FraseSustentavel> fraseSustentaveis = fraseSustentavelRepository.findAll();
        return fraseSustentaveis.isEmpty() ? null : fraseSustentaveis;
    }

    /**
     * @return fraseSustentavel pelo id, se ela existir, caso contrário, retorna null
     */
    public FraseSustentavel getFraseSustentavelById(Long id) {
        Optional<FraseSustentavel> fraseSustentavel = fraseSustentavelRepository.findById(id);
        return fraseSustentavel.orElse(null);
    }

    /**
     * @return fraseSustentavel deletada.
     */
    public FraseSustentavel deleteFraseSustentavelById(Long id) {
        FraseSustentavel fraseSustentavel = getFraseSustentavelById(id);
        if (fraseSustentavel != null) fraseSustentavelRepository.deleteById(id);
        return fraseSustentavel;
    }

    /**
     * @return fraseSustentavel inserida.
     */
    public FraseSustentavel saveFraseSustentavel(FraseSustentavel fraseSustentavel) {
        fraseSustentavel.setDtAtualizacao(LocalDateTime.now());

        return fraseSustentavelRepository.save(fraseSustentavel);
    }

    /**
     * Atualiza os dados de uma fraseSustentavel.
     * @param id Id da fraseSustentavel a ser atualizada.
     * @param fraseSustentavel FraseSustentavel com os novos dados.
     * @return fraseSustentavel atualizada ou nulo, caso a fraseSustentavel não exista
     */
    public FraseSustentavel updateFraseSustentavel(Long id, FraseSustentavel fraseSustentavel) {
        FraseSustentavel existingFraseSustentavel = getFraseSustentavelById(id);
        if (existingFraseSustentavel != null) {
            existingFraseSustentavel.setDsFrase(fraseSustentavel.getDsFrase());
            existingFraseSustentavel.setDtFrase(fraseSustentavel.getDtFrase());
            existingFraseSustentavel.setDtAtualizacao(LocalDateTime.now());
            return fraseSustentavelRepository.save(existingFraseSustentavel);
        }
        return null;
    }

}
