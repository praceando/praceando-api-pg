/*
 * Class: FraseSustentavel
 * Description: Model for the sustainable_phrase entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 23/08/2024
 * Last Updated: 13/10/2024
 */
package blomera.praceando.praceandoapipg.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "frase_sustentavel")
@Schema(description = "Representa uma frase sustentável.")
public class FraseSustentavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_frase_sustentavel")
    @Schema(description = "Identificador único da frase sustentável.", example = "1")
    private long id;

    @NotNull(message = "O campo 'ds_frase_sustentavel' (descrição da frase) não pode ser nulo.")
    @Size(min = 3, max = 150, message = "A descrição da frase ('ds_frase') deve ter pelo menos 3 caracteres e no máximo 150.")
    @Column(name = "ds_frase_sustentavel")
    @Schema(description = "Descrição da frase sustentável.", example = "Reduzir, Reutilizar, Reciclar")
    private String dsFrase;

    @NotNull(message = "O campo 'dt_frase_sustentavel' (data da frase) não pode ser nulo.")
    @Column(name = "dt_frase_sustentavel")
    @Schema(description = "Data e hora da frase.", example = "2024-08-18T10:00:00")
    private LocalDateTime dtFrase;

    @NotNull(message = "O campo 'dt_atualizacao' (data de atualização) não pode ser nulo.")
    @Past(message = "A data de atualização ('dt_atualizacao') deve estar no passado.")
    @Column(name = "dt_atualizacao")
    @Schema(description = "Data e hora da última atualização da frase.", example = "2024-08-18T10:00:00")
    private LocalDateTime dtAtualizacao;
}
