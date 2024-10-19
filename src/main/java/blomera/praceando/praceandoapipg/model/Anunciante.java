/*
 * Class: Anunciante
 * Description: Model for the Anunciante entity, (inherits from Usuario).
 * Author: Camilla Ucci de Menezes
 * Creation Date: 30/08/2024
 * Last Updated: 10/10/2024
 */
package blomera.praceando.praceandoapipg.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity(name = "anunciante")
@PrimaryKeyJoinColumn(name = "id_anunciante")
@Schema(description = "Representa um anunciante, que herda de usuário no sistema Praceando.")
public class Anunciante extends Usuario {
    @Past(message = "A data de nascimento ('dt_nascimento') deve ser no passado.")
    @Column(name = "dt_nascimento", nullable = false)
    @Schema(description = "Data de nascimento do anunciante.", example = "1980-06-15")
    private LocalDate dtNascimento;

    @NotBlank(message = "O nome da empresa ('nm_empresa') não pode estar vazio.")
    @Column(name = "nm_empresa", length = 255)
    @Schema(description = "Nome da empresa do anunciante.", example = "Acme Corp.")
    private String nmEmpresa;

    @NotBlank(message = "O CNPJ ('nr_cnpj') não pode estar vazio.")
    @Pattern(regexp = "\\d{14}", message = "O CNPJ ('nr_cnpj') deve conter exatamente 14 dígitos.")
    @Column(name = "nr_cnpj", unique = true, length = 14)
    @Schema(description = "CNPJ da empresa do anunciante, deve ser único e ter 14 dígitos.", example = "12345678000195")
    private String nrCnpj;

    @NotBlank(message = "O telefone ('nr_telefone') não pode estar vazio.")
    @Pattern(regexp = "\\d{14}", message = "O telefone ('nr_telefone') deve conter exatamente 14 dígitos.")
    @Column(name = "nr_telefone", unique = true, length = 14)
    @Schema(description = "Número de telefone do anunciante, deve ser único e ter 14 dígitos.", example = "11987654321")
    private String nrTelefone;

    @PrePersist
    @PreUpdate
    private void validateAge() {
        if (Period.between(dtNascimento, LocalDate.now()).getYears() < 16) {
            throw new IllegalArgumentException("A idade do anunciante deve ser maior ou igual a 16 anos.");
        }
    }
}
