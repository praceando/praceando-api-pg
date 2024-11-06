package blomera.praceando.praceandoapipg.authentication.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
@Schema(description = "Representa o login no sistema Praceando.")
public class LoginRequest {
    @Email(message = "O e-mail ('ds_email') deve ser um e-mail válido.")
    @NotBlank(message = "O e-mail ('ds_email') não pode estar vazio.")
    @Schema(description = "E-mail do usuário.", example = "camis.linda@example.com")
    private String dsEmail;

    @Size(min = 8, message = "A senha ('ds_senha') deve ter pelo menos 8 caracteres.")
    @NotBlank(message = "A senha ('ds_senha') não pode estar vazia.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+$",
            message = "A senha ('ds_senha') deve conter pelo menos um número e um caractere especial.")
    @Column(name = "ds_senha", length = 255)
    @Schema(description = "Senha do usuário.", example = "Senha123@")
    private String dsSenha;
}