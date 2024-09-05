/*
 * Class: Local
 * Description: Model for the Local entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 26/08/2024
 * Last Updated: 30/08/2024
 */
package blomera.praceando.praceandoapipg.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "local")
@Schema(description = "Representa um local utilizado no sistema Praceando.")
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_local")
    @Schema(description = "Identificador único do local.", example = "1")
    private long id;

    @Size(max = 255, message = "O nome do local ('nm_local') deve ter no máximo 255 caracteres.")
    @Column(name = "nm_local")
    @Schema(description = "Nome do local.", example = "Praça Central")
    private String nmLocal;

    @NotNull(message = "A latitude ('nr_lat') não pode ser nula.")
    @Column(name = "nr_lat", precision = 10, scale = 6)
    @Schema(description = "Latitude do local com precisão de até 10 cm.", example = "37.774929")
    private BigDecimal nrLat;

    @NotNull(message = "A longitude ('nr_long') não pode ser nula.")
    @Column(name = "nr_long", precision = 10, scale = 6)
    @Schema(description = "Longitude do local com precisão de até 10 cm.", example = "-122.419418")
    private BigDecimal nrLong;

    @Past(message = "A data de atualização ('dt_atualizacao') deve estar no passado.")
    @Column(name = "dt_atualizacao")
    @Schema(description = "Data e hora da última atualização do local.", example = "2024-08-18T10:00:00")
    private LocalDateTime dtAtualizacao;
}
