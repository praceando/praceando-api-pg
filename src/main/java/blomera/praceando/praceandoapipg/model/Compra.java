/*
 * Class: Compra
 * Description: Model for the Compra entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 05/09/2024
 * Last Updated: 13/10/2024
 */
package blomera.praceando.praceandoapipg.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "compra")
@Schema(description = "Representa uma compra realizada no sistema Praceando.")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compra")
    @Schema(description = "Identificador único da compra.", example = "1")
    private long id;

    @ManyToOne
    @JoinColumn(name = "cd_usuario", referencedColumnName = "id_usuario")
    @Schema(description = "Usuário associado à compra.")
    private Usuario usuario;

    @ManyToMany
    @JoinTable(name = "compra_produto",
            joinColumns = @JoinColumn(name = "cd_compra", referencedColumnName = "id_compra"),
            inverseJoinColumns = @JoinColumn(name = "cd_produto", referencedColumnName = "id_produto"))
    @Schema(description = "Produtos adquiridos na compra.")
    private List<Produto> produtos;

    @ManyToOne
    @JoinColumn(name = "cd_evento", referencedColumnName = "id_evento")
    @Schema(description = "Evento relacionado à compra.")
    private Evento evento;

    @Column(name = "dt_compra", columnDefinition = "TIMESTAMP DEFAULT CURRENT_DATE")
    @Schema(description = "Data e hora da realização da compra.", example = "2024-08-18T10:00:00")
    private LocalDateTime dtCompra;

    @DecimalMin(value = "0.0", message = "O valor total ('vl_total') deve ser maior ou igual a 0.")
    @Column(name = "vl_total", precision = 10, scale = 2)
    @Schema(description = "Valor total da compra.", example = "199.99")
    private BigDecimal vlTotal;

    @Size(max = 255, message = "O status da compra ('ds_status') deve ter no máximo 255 caracteres.")
    @Column(name = "ds_status")
    @Schema(description = "Status atual da compra.", example = "Pendente")
    private String dsStatus;

    @Column(name = "dt_atualizacao", columnDefinition = "TIMESTAMP DEFAULT CURRENT_DATE")
    @Schema(description = "Data e hora da última atualização da compra.", example = "2024-08-18T10:00:00")
    private LocalDateTime dtAtualizacao;
}
