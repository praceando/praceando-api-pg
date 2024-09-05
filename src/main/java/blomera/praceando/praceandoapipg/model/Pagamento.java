/*
 * Class: Pagamento
 * Description: Model for the Pagamento entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 05/09/2024
 * Last Updated: 05/09/2024
 */
package blomera.praceando.praceandoapipg.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "pagamento")
@Schema(description = "Representa um pagamento no sistema Praceando.")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagamento")
    @Schema(description = "Identificador único do pagamento.", example = "1")
    private long id;

    @Column(name = "dt_pagamento")
    @Schema(description = "Data e hora em que o pagamento foi realizado.", example = "2024-09-01T10:00:00")
    private LocalDateTime dtPagamento;

    @Column(name = "dt_atualizacao")
    @Schema(description = "Data e hora da última atualização do pagamento.", example = "2024-09-01T12:00:00")
    private LocalDateTime dtAtualizacao;

    @ManyToOne
    @JoinColumn(name = "cd_compra", referencedColumnName = "id_compra")
    @Schema(description = "Compra associada a este pagamento.")
    private Compra compra;
}
