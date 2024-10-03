/*
 * Class: Evento
 * Description: Model for the Evento entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 05/09/2024
 * Last Updated: 05/09/2024
 */
package blomera.praceando.praceandoapipg.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "evento")
@Schema(description = "Representa um evento no sistema Praceando.")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    @Schema(description = "Identificador único do evento.", example = "1")
    private long id;

    @Column(name = "qt_interesse")
    @Schema(description = "Quantidade de pessoas interessadas no evento.", example = "350")
    private int qtInteresse;

    @Size(max = 255, message = "O nome do evento ('nm_evento') deve ter no máximo 255 caracteres.")
    @Column(name = "nm_evento")
    @Schema(description = "Nome do evento.", example = "Festival de Sustentabilidade")
    private String nmEvento;

    @Column(name = "ds_evento", columnDefinition = "TEXT")
    @Schema(description = "Descrição detalhada do evento.", example = "Um evento que promove práticas sustentáveis.")
    private String dsEvento;

    @Column(name = "dt_inicio")
    @Schema(description = "Data de início do evento.", example = "2024-09-01")
    private LocalDate dtInicio;

    @Column(name = "hr_inicio")
    @Schema(description = "Hora de início do evento.", example = "18:00:00")
    private LocalTime hrInicio;

    @Column(name = "dt_fim")
    @Schema(description = "Data de término do evento.", example = "2024-09-02")
    private LocalDate dtFim;

    @Column(name = "hr_fim")
    @Schema(description = "Hora de término do evento.", example = "20:00:00")
    private LocalTime hrFim;

    @Column(name = "url_documentacao", columnDefinition = "TEXT")
    @Schema(description = "URL com documentação adicional sobre o evento.", example = "https://www.exemplo.com/documentacao")
    private String urlDocumentacao;

    @Column(name = "dt_atualizacao", columnDefinition = "TIMESTAMP DEFAULT CURRENT_DATE")
    @Schema(description = "Data e hora da última atualização do evento.", example = "2024-08-18T10:00:00")
    private LocalDateTime dtAtualizacao;

    @ManyToOne
    @JoinColumn(name = "cd_local", referencedColumnName = "id_local")
    @Schema(description = "Praça onde o evento será realizado.")
    private Local local;

    @ManyToOne
    @JoinColumn(name = "cd_anunciante", referencedColumnName = "id_usuario")
    @Schema(description = "Anunciante responsável pelo evento.")
    private Usuario anunciante;
}
