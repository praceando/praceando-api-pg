/*
 * Class: PagamentoService
 * Description: Service for the Pagamento entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 18/09/2024
 * Last Updated: 18/09/2024
 */

package blomera.praceando.praceandoapipg.service;

import blomera.praceando.praceandoapipg.model.Pagamento;
import blomera.praceando.praceandoapipg.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    /**
     * @return uma lista de objetos Pagamento se existirem, ou null se não houver nenhum pagamento.
     */
    public List<Pagamento> getPagamentos() {
        List<Pagamento> pagamentos = pagamentoRepository.findAll();
        return pagamentos.isEmpty() ? null : pagamentos;
    }

    /**
     * @return pagamento pelo id, se ele existir, caso contrário, retorna null
     */
    public Pagamento getPagamentoById(Long id) {
        Optional<Pagamento> pagamento = pagamentoRepository.findById(id);
        return pagamento.orElse(null);
    }

    /**
     * @return pagamento deletado.
     */
    public Pagamento deletePagamentoById(Long id) {
        Pagamento pagamento = getPagamentoById(id);
        if (pagamento != null) pagamentoRepository.deleteById(id);
        return pagamento;
    }

    /**
     * @return pagamento inserido.
     */
    public Pagamento savePagamento(Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    /**
     * Atualiza os dados de um pagamento.
     * @param id Id do pagamento a ser atualizado.
     * @param pagamento Pagamento com os novos dados.
     * @return pagamento atualizado ou nulo, caso ele não exista
     */
    public Pagamento updatePagamento(Long id, Pagamento pagamento) {
        Pagamento existingPagamento = getPagamentoById(id);
        if (existingPagamento != null) {
            existingPagamento.setDtPagamento(pagamento.getDtPagamento());
            existingPagamento.setDtAtualizacao(pagamento.getDtAtualizacao());
            existingPagamento.setCompra(pagamento.getCompra());
            return pagamentoRepository.save(existingPagamento);
        }
        return null;
    }
}
