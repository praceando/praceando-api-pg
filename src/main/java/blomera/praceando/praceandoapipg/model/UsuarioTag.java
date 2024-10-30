/*
 * Class: UsuarioTag
 * Description: Model for the UsuarioTag entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 30/10/2024
 * Last Updated: 30/10/2024
 */
package blomera.praceando.praceandoapipg.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "usuario_tag")
@Schema(description = "Representa a associação entre um usuário e uma tag.")
public class UsuarioTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario_tag")
    @Schema(description = "Identificador único da associação entre usuário e tag.")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cd_consumidor", referencedColumnName = "id_usuario")
    @Schema(description = "Consumidor associado à tag.", example = "{\"id\": 1}")
    private Usuario consumidor;

    @ManyToOne
    @JoinColumn(name = "cd_tag", referencedColumnName = "id_tag")
    @Schema(description = "Tag associada ao consumidor.", example = "{\"id\": 2}")
    private Tag tag;
}
