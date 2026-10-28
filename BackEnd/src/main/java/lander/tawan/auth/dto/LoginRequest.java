package lander.tawan.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @Email(message = "'email' deve ser válido.")
        @NotBlank(message = "'email' deve possuir conteúdo.")
        @Size(max = 255, min = 3, message = "'email' deve possuir de 3 a 255 caracteres")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$", message = "'email' deve ser válido.")
        String email,

        @NotBlank(message = "'password' deve possuir conteúdo.")
        @Size(max = 72, min = 8, message = "'password' deve possuir entre 8 e 72 caracteres")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\p{L}\\p{N}\\s]).{8,}$", message = "'password' deve ter no mínimo 8 caracteres, com maiúscula, minúscula, número e caractere especial")
        String password
){}
