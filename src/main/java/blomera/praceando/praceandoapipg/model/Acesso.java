/*
 * Class: Acesso
 * Description: Model for the Acesso entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 30/09/2024
 * Last Updated: 01/10/2024
 */
package blomera.praceando.praceandoapipg.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "acesso")
@Schema(description = "Representa um acesso no sistema Praceando.")
public class Acesso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_acesso", nullable = false)
    @Schema(description = "Identificador único do acesso.", example = "1")
    private Long id;

    @NotBlank(message = "O nome de acesso ('nm_acesso') não pode estar vazio.")
    @Column(name = "nm_acesso", nullable = false, unique = true, length = 255)
    @Schema(description = "Nome de acesso, deve ser único.", example = "ADMIN")
    private String nmAcesso;

    @Column(name = "dt_atualizacao", columnDefinition = "TIMESTAMP DEFAULT CURRENT_DATE")
    @Schema(description = "Data e hora da última atualização do evento.", example = "2024-08-18T10:00:00")
    private LocalDateTime dtAtualizacao;
}
