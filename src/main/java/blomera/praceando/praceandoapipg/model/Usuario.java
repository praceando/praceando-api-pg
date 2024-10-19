/*
 * Class: Usuario
 * Description: Model for the Usuario entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 30/08/2024
 * Last Updated: 10/10/2024
 */
package blomera.praceando.praceandoapipg.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Inheritance(strategy = InheritanceType.JOINED)
@Entity(name = "usuario")
@Schema(description = "Representa um usuário do sistema Praceando.")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    @Schema(description = "Identificador único do usuário.", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cd_acesso", referencedColumnName = "id_acesso")
    @Schema(description = "Acessos do usuário.", example = "2")
    private Acesso acesso;

    @Column(name = "cd_inventario_avatar")
    @Schema(description = "Código do avatar do usuário.", example = "101")
    private Integer cdInventarioAvatar;

    @ManyToOne
    @JoinColumn(name = "cd_genero", referencedColumnName = "id_genero")
    @Schema(description = "Gênero do usuário.", example = "2")
    private Genero genero;

    @Column(name = "cd_tipo_usuario")
    @Schema(description = "Código do tipo do usuário.", example = "101")
    private Integer cdTipoUsuario;

    @NotBlank(message = "O nome do usuário ('nm_usuario') não pode estar vazio.")
    @Column(name = "nm_usuario", length = 255)
    @Schema(description = "Nome do usuário.", example = "Camilla Ucci")
    private String nmUsuario;

    @Email(message = "O e-mail ('ds_email') deve ser um e-mail válido.")
    @NotBlank(message = "O e-mail ('ds_email') não pode estar vazio.")
    @Column(name = "ds_email", length = 255)
    @Schema(description = "E-mail do usuário.", example = "camis.linda@example.com")
    private String dsEmail;

    @Size(min = 8, message = "A senha ('ds_senha') deve ter pelo menos 8 caracteres.")
    @NotBlank(message = "A senha ('ds_senha') não pode estar vazia.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+$",
            message = "A senha ('ds_senha') deve conter pelo menos um número e um caractere especial.")
    @Column(name = "ds_senha", length = 255)
    @Schema(description = "Senha do usuário.", example = "Senha123@")
    private String dsSenha;

    @Column(name = "is_premium")
    @Schema(description = "Indica se o usuário é um assinante premium.", example = "true")
    private Boolean isPremium;

    @Column(name = "ds_usuario")
    @Schema(description = "Descrição do usuário.", example = "Descrição adicional sobre o usuário.")
    private String dsUsuario;

    @Column(name = "dt_criacao", columnDefinition = "TIMESTAMP DEFAULT CURRENT_DATE")
    @Schema(description = "Data e hora da criação do usuário.", example = "2024-08-27T10:00:00")
    private LocalDateTime dtCriacao;

    @Column(name = "dt_desativacao")
    @Schema(description = "Data e hora da desativação do usuário.", example = "2024-08-31T15:30:00")
    private LocalDateTime dtDesativacao;

    @Column(name = "dt_atualizacao", columnDefinition = "TIMESTAMP DEFAULT CURRENT_DATE")
    @Schema(description = "Data e hora da última atualização do usuário.", example = "2024-08-27T10:00:00")
    private LocalDateTime dtAtualizacao;
}
