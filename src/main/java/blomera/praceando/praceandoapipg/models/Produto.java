/*
 * Class: Produto
 * Description: Model for the Produto entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 27/08/2024
 * Last Updated: 27/08/2024
 */
package blomera.praceando.praceandoapipg.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "produto")
@Schema(description = "Representa um produto utilizado no sistema Praceando.")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    @Schema(description = "Identificador único do produto.", example = "1")
    private long id;

    @Column(name = "qt_estoque", nullable = false)
    @Schema(description = "Quantidade de itens em estoque do produto.", example = "10")
    private int qtEstoque;

    @Size(max = 255, message = "O nome do produto ('nm_produto') deve ter no máximo 255 caracteres.")
    @Column(name = "nm_produto")
    @Schema(description = "Nome do produto.", example = "Bife de alcatra")
    private String nmProduto;

    @Column(name = "ds_produto")
    @Schema(description = "Descrição do produto.", example = "Corte de carne bovina de alta qualidade.")
    private String dsProduto;

    @NotNull(message = "O preço ('vl_preco') não pode ser nulo.")
    @DecimalMin(value = "1.00", message = "O preço ('vl_preco') deve ser no mínimo 1.00.")
    @Column(name = "vl_preco", precision = 10, scale = 2)
    @Schema(description = "Preço do produto.", example = "29.99")
    private BigDecimal vlPreco;

    @Column(name = "url_imagem")
    @Schema(description = "URL da imagem do produto.", example = "https://exemplo.com/imagem.jpg")
    private String urlImagem;

    @Size(max = 255, message = "O nome da categoria ('nm_categoria') deve ter no máximo 255 caracteres.")
    @Column(name = "nm_categoria")
    @Schema(description = "Nome da categoria do produto.", example = "Carnes")
    private String nmCategoria;

    @Column(name = "dt_atualizacao")
    @Schema(description = "Data e hora da última atualização do produto.", example = "2024-08-18T10:00:00")
    private LocalDateTime dtAtualizacao;

    @Column(name = "dt_desativacao")
    @Schema(description = "Data e hora da desativação do produto.", example = "2024-08-25T15:30:00")
    private LocalDateTime dtDesativacao;
}
