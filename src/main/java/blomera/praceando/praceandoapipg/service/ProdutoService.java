/*
 * Class: ProdutoService
 * Description: Service for the Usuario entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 09/09/2024
 * Last Updated: 09/09/2024
 */
package blomera.praceando.praceandoapipg.service;

import blomera.praceando.praceandoapipg.model.Anunciante;
import blomera.praceando.praceandoapipg.model.Produto;
import blomera.praceando.praceandoapipg.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    /**
     * @return uma lista de objetos Produto se existirem, ou null se não houver nenhum produto.
     */
    public List<Produto> getProdutos() {
        List<Produto> products = produtoRepository.findAll();
        return products.isEmpty() ? null : products;
    }

    /**
     * @return produto pelo id, se ele existir, caso contrário, retorna null
     */
    public Produto getProdutoById(Long id) {
        Optional<Produto> product = produtoRepository.findById(id);
        return product.orElse(null);
    }

    /**
     * @return produto deletado.
     */
    public Produto deleteProdutoById(Long id) {
        Produto product = getProdutoById(id);
        if (product != null) produtoRepository.deleteById(id);
        return product;
    }

    /**
     * @return produto inserido.
     */
    public Produto saveProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    /**
     * Atualiza o estoque de um produto ao realizar uma compra.
     * @param productId Id do produto a ser atualizado.
     * @param quantidadeComprada Quantidade de produtos comprada.
     * @return produto atualizado, retorna null se o produto não existir ou não houver estoque suficiente
     */
    public Produto updateEstoqueProduto(Long productId, int quantidadeComprada) {
        Produto product = getProdutoById(productId);
        if (product != null && product.getQtEstoque() >= quantidadeComprada) {
            product.setQtEstoque(product.getQtEstoque() - quantidadeComprada);
            return produtoRepository.save(product);
        }
        return null;
    }

    /**
     * Atualiza os dados de um produto.
     * @param id Id do produto a ser atualizado.
     * @param produto Produto com os novos dados.
     * @return produto atualizado, retorna null se o produto não existir
     */
    public Produto updateProduto(Long id, Produto produto) {
        Produto existingProduto = getProdutoById(id);
        if (existingProduto != null) {
            existingProduto.setQtEstoque(produto.getQtEstoque());
            existingProduto.setNmProduto(produto.getNmProduto());
            existingProduto.setDsProduto(produto.getDsProduto());
            existingProduto.setVlPreco(produto.getVlPreco());
            existingProduto.setVlPreco(produto.getVlPreco());
            return produtoRepository.save(existingProduto);
        }
        return null;
    }
}
