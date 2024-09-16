/*
 * Class: CompraService
 * Description: Service for the Compra entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 16/09/2024
 * Last Updated: 16/09/2024
 */

package blomera.praceando.praceandoapipg.service;

import blomera.praceando.praceandoapipg.model.Compra;
import blomera.praceando.praceandoapipg.repository.CompraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompraService {

    private final CompraRepository compraRepository;

    public CompraService(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    /**
     * @return uma lista de objetos Compra se existirem, ou null se não houver nenhuma compra.
     */
    public List<Compra> getCompras() {
        List<Compra> compras = compraRepository.findAll();
        return compras.isEmpty() ? null : compras;
    }

    /**
     * @return compra pelo id, se ela existir, caso contrário, retorna null
     */
    public Compra getCompraById(Long id) {
        Optional<Compra> compra = compraRepository.findById(id);
        return compra.orElse(null);
    }

    /**
     * @return compra deletada.
     */
    public Compra deleteCompraById(Long id) {
        Compra compra = getCompraById(id);
        if (compra != null) compraRepository.deleteById(id);
        return compra;
    }

    /**
     * @return compra inserida.
     */
    public Compra saveCompra(Compra compra) {
        return compraRepository.save(compra);
    }

    /**
     * Atualiza os dados de uma compra.
     * @param id Id da compra a ser atualizada.
     * @param compra Compra com os novos dados.
     * @return compra atualizada ou nulo, caso a compra não exista
     */
    public Compra updateCompra(Long id, Compra compra) {
        Compra existingCompra = getCompraById(id);
        if (existingCompra != null) {
            existingCompra.setDtCompra(compra.getDtCompra());
            existingCompra.setVlTotal(compra.getVlTotal());
            existingCompra.setDsStatus(compra.getDsStatus());
            existingCompra.setDtAtualizacao(compra.getDtAtualizacao());
            existingCompra.setUsuario(compra.getUsuario());
            existingCompra.setProduto(compra.getProduto());
            existingCompra.setEvento(compra.getEvento());
            return compraRepository.save(existingCompra);
        }
        return null;
    }
}
