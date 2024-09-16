/*
 * Class: ConsumidorService
 * Description: Service for the Consumidor entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 16/09/2024
 * Last Updated: 16/09/2024
 */

package blomera.praceando.praceandoapipg.service;

import blomera.praceando.praceandoapipg.model.Consumidor;
import blomera.praceando.praceandoapipg.repository.ConsumidorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsumidorService {

    private final ConsumidorRepository consumidorRepository;

    public ConsumidorService(ConsumidorRepository consumidorRepository) {
        this.consumidorRepository = consumidorRepository;
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
            existingConsumidor.setDtNascimento(consumidor.getDtNascimento());
            existingConsumidor.setNmNickname(consumidor.getNmNickname());
            existingConsumidor.setNrPolen(consumidor.getNrPolen());
            return consumidorRepository.save(existingConsumidor);
        }
        return null;
    }
}
