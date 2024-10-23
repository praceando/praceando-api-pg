/*
 * Class: Interesse
 * Description: Model for the Interesse entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 05/09/2024
 * Last Updated: 05/09/2024
 */
package blomera.praceando.praceandoapipg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Entity(name = "interesse")
@Schema(description = "Representa o interesse de um consumidor em um evento no sistema Praceando.")
public class Interesse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_interesse")
    @Schema(description = "Identificador único do interesse.", example = "1")
    private long id;

    @ManyToOne
    @JoinColumn(name = "cd_consumidor", referencedColumnName = "id_usuario")
    @Schema(description = "Usuário que demonstrou interesse no evento.")
    private Usuario consumidor;

    @ManyToOne
    @JoinColumn(name = "cd_evento", referencedColumnName = "id_evento")
    @Schema(description = "Evento em que o usuário demonstrou interesse.")
    private Evento evento;

    @Column(name = "dt_atualizacao", columnDefinition = "TIMESTAMP DEFAULT CURRENT_DATE")
    @Schema(description = "Data e hora da última atualização do interesse.", example = "2024-08-18T10:00:00")
    private LocalDateTime dtAtualizacao;
}
