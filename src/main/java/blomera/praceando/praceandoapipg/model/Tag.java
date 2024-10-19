/*
 * Class: Tag
 * Description: Model for the Tag entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 27/08/2024
 * Last Updated: 27/08/2024
 */
package blomera.praceando.praceandoapipg.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "tag")
@Schema(description = "Representa uma tag utilizada no sistema Praceando.")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tag")
    @Schema(description = "Identificador único da tag.", example = "1")
    private long id;

    @Size(max = 255, message = "O nome da tag ('nm_tag') deve ter no máximo 255 caracteres.")
    @Column(name = "nm_tag")
    @Schema(description = "Nome da tag.", example = "Sustentabilidade")
    private String nmTag;

    @Size(max = 255, message = "A descrição da categoria ('ds_categoria') deve ter no máximo 255 caracteres.")
    @Column(name = "ds_categoria")
    @Schema(description = "Descrição da categoria da tag.", example = "Categoria ambiental")
    private String dsCategoria;

    @Column(name = "dt_atualizacao")
    @Schema(description = "Data e hora da última atualização da tag.", example = "2024-08-18T10:00:00")
    private LocalDateTime dtAtualizacao;
}
