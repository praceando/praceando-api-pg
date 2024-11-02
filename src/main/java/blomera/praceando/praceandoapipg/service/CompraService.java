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
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final JdbcTemplate jdbcTemplate;

    public CompraService(CompraRepository compraRepository, JdbcTemplate jdbcTemplate) {
        this.compraRepository = compraRepository;
        this.jdbcTemplate = jdbcTemplate;
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
     * Método para inserir uma compra.
     */
    public void saveCompra(Integer cdUsuario, Integer cdProduto, Integer cdEvento, BigDecimal vlTotal) {
        jdbcTemplate.execute((ConnectionCallback<Void>) con -> {
            try (CallableStatement callableStatement = con.prepareCall("CALL PRC_REALIZAR_COMPRA(?, ?, ?, ?)")) {
                callableStatement.setInt(1, cdUsuario);

                if (cdProduto != null) {
                    callableStatement.setInt(2, cdProduto);
                } else {
                    callableStatement.setNull(2, Types.INTEGER);
                }

                if (cdEvento != null) {
                    callableStatement.setInt(3, cdEvento);
                } else {
                    callableStatement.setNull(3, Types.INTEGER);
                }

                callableStatement.setBigDecimal(4, vlTotal);

                callableStatement.execute();
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao realizar compra: " + e.getMessage(), e);
            }
        });
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
            existingCompra.setUsuario(compra.getUsuario());
            existingCompra.setProdutos(compra.getProdutos());
            existingCompra.setEvento(compra.getEvento());
            existingCompra.setDtCompra(compra.getDtCompra());
            existingCompra.setVlTotal(compra.getVlTotal());
            existingCompra.setDsStatus(compra.getDsStatus());
            existingCompra.setDtAtualizacao(LocalDateTime.now());
            return compraRepository.save(existingCompra);
        }
        return null;
    }
}
