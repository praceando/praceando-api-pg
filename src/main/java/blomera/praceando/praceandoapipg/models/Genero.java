/*
 * Class: Genero
 * Description: Model for the Genero entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 23/08/2024
 * Last Updated: 30/08/2024
 */
package blomera.praceando.praceandoapipg.models;

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
@Entity(name = "genero")
@Schema(description = "Representa o gênero utilizado no sistema Praceando.")
public class Genero {

    @Id
    @Column(name = "id_genero")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do gênero.", example = "1")
    private long id;

    @NotNull(message = "O campo 'nome' (nome do gênero) não pode ser nulo.")
    @Size(min = 3, max = 255, message = "O nome do gênero ('nome') deve ter pelo menos 3 caracteres e no máximo 255.")
    @Column(name = "ds_nome")
    @Schema(description = "Nome do gênero.", example = "Feminino")
    private String dsNome;

    @NotNull(message = "O campo 'dt_atualizacao' (data de atualização) não pode ser nulo.")
    @Past(message = "A data de atualização ('dt_atualizacao') deve estar no passado.")
    @Column(name = "dt_atualizacao")
    @Schema(description = "Data e hora da última atualização do gênero.", example = "2024-08-18T10:00:00")
    private LocalDateTime dtAtualizacao;
}
