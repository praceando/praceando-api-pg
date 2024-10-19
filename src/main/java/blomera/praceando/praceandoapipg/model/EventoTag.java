/*
 * Class: EventoTag
 * Description: Model for the EventoTag entity.
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
@Entity(name = "evento_tag")
@Schema(description = "Representa a relação entre eventos e tags no sistema Praceando (tabela intermediária).")
public class EventoTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento_tag")
    @Schema(description = "Identificador único da relação entre evento e tag.", example = "1")
    private long id;

    @ManyToOne
    @JoinColumn(name = "cd_tag", referencedColumnName = "id_tag")
    @Schema(description = "Tag associada ao evento.")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "cd_evento", referencedColumnName = "id_evento")
    @Schema(description = "Evento associado à tag.")
    private Evento evento;

    @Column(name = "dt_atualizacao", columnDefinition = "TIMESTAMP DEFAULT CURRENT_DATE")
    @Schema(description = "Data e hora da última atualização da relação.", example = "2024-08-18T10:00:00")
    private LocalDateTime dtAtualizacao;
}
