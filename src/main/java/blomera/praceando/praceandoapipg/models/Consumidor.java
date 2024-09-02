/*
 * Class: Consumidor
 * Description: Model for the Consumidor entity, (inherits from Usuario).
 * Author: Camilla Ucci de Menezes
 * Creation Date: 30/08/2024
 * Last Updated: 30/08/2024
 */
package blomera.praceando.praceandoapipg.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity(name = "consumidor")
@Schema(description = "Representa um consumidor, que herda de usuário no sistema Praceando.")
public class Consumidor extends Usuario {

    @Past(message = "A data de nascimento ('dt_nascimento') deve ser no passado.")
    @Column(name = "dt_nascimento", nullable = false)
    @Schema(description = "Data de nascimento do consumidor.", example = "2000-01-15")
    private LocalDate dtNascimento;

    @Column(name = "nm_nickname", unique = true)
    @Schema(description = "Nickname do consumidor, deve ser único.", example = "milla123")
    private String nmNickname;

    @Column(name = "nr_polen", columnDefinition = "INT DEFAULT 0")
    @Schema(description = "Quantidade de pólen do consumidor.", example = "50")
    private Integer nrPolen;
}
