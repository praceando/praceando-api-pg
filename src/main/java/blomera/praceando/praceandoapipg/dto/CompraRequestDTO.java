package blomera.praceando.praceandoapipg.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompraRequestDTO {
    private Long cdUsuario;
    private Long cdProduto;
    private Long cdEvento;
    private BigDecimal vlTotal;
}
