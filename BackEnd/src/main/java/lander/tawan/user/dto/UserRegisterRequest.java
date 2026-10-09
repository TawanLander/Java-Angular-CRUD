package lander.tawan.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record UserRegisterRequest(
        @Schema(description = "Nome completo", example = "João Gomes")
        @NotBlank(message = "name é obrigatório")
        String name,

        @Schema(description = "Telefone com DDD, apenas números", example = "1120023003")
        @NotBlank(message = "phone é obrigatório")
        @Size(max = 11, message = "phone não pode ter mais que 11 caracteres")
        String phone,

        @Schema(description = "E-mail único", example = "exemplo@gmail.com")
        @NotBlank(message = "email é obrigatório")
        @Email(message = "email deve seguir a semântica padrão")
        @Pattern(regexp = ".*\\S.*", message = "email não pode ser vazio")
        String email
){}
