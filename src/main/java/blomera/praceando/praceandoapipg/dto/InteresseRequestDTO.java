package blomera.praceando.praceandoapipg.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InteresseRequestDTO {
    private Integer idEvento;
    private Integer idUsuario;
    private List<String> tags;
}
