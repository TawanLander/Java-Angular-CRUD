package lander.tawan.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserPatchRequest(
        @Schema(description = "Nome completo", example = "João Gomes")
        String name,

        @Schema(description = "Telefone com DDD, apenas números", example = "1120023003")
        @Size(message = "phone não pode ter mais que 11 caracteres", max = 11)
        String phone,

        @Schema(description = "E-mail único", example = "exemplo@gmail.com")
        @Pattern(regexp = ".*\\S.*", message = "email não pode ser vazio")
        @Email(message = "email deve seguir a semântica padrão")
        String email
){
        @AssertTrue(message = "Ao menos um campo deve ser inserido")
        public boolean isAtLeastOneFieldPresent() {
                return isFilled(name) || isFilled(phone) || isFilled(email);
        }

        private static boolean isFilled(String value) {
                return value != null && !value.isBlank();
        }
}
