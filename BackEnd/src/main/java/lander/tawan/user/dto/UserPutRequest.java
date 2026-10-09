package lander.tawan.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserPutRequest(
        @Schema(description = "Nome completo", example = "João Gomes")
        @NotBlank(message = "name é obrigatório")
        String name,

        @Schema(description = "Telefone com DDD, apenas números", example = "1120023003")
        @Pattern(regexp = "\\d{10,11}", message = "phone deve conter 10 ou 11 dígitos")
        @NotBlank(message = "phone é obrigatório")
        String phone,

        @Schema(description = "E-mail único", example = "exemplo@gmail.com")
        @Email(message = "email deve seguir a semântica padrão")
        @Pattern(regexp = ".*\\S.*", message = "email não pode ser vazio")
        @NotBlank(message = "email é obrigatório")
        String email
){}
