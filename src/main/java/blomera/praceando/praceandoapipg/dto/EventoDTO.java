package blomera.praceando.praceandoapipg.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idEvento;
    private String nomeEvento;
    private String nomeLocal;
    private LocalDate dataInicio;
    private LocalTime horaInicio;
    private LocalDate dataFim;
    private LocalTime horaFim;
    private List<String> tags;
}
