package blomera.praceando.praceandoapipg.dto;

import blomera.praceando.praceandoapipg.model.Evento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventoRequest {
    private Evento evento;
    private List<String> tags;
}

