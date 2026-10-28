package lander.tawan.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lander.tawan.auth.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Auth", description = "Gerenciamento da sessão dos usuários")
@RequestMapping("/auth")
public interface AuthApi {

    @Operation(summary = "Autentica o usuário no sistema", description = "Retorna erros genéricos ao verificar as credenciais inseridas para não expor os emails já cadastrados no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário autenticado"),
            @ApiResponse(responseCode = "400", description = "Corpo da requisição mal formatado"),
            @ApiResponse(responseCode = "401", description = "Credenciais de acesso inválidas")
    })
    @PostMapping("/login")
    ResponseEntity<Void> login(@Valid @RequestBody LoginRequest req);

    @Operation(summary = "Remove as credenciais de acesso do usuário no sistema", description = "Remove os tokens do usuário, impossibilitando novas requisições permissionadas, é indepotente, retornando '204' sempre")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Credenciais removidas")
    })
    @PostMapping("/logout")
    ResponseEntity<Void> logout();

    @Operation(summary = "Renova as credenciais de acesso do usuário", description = "Renova os tokens de acesso, entrega um novo par de tokens e invalida o refresh token usado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Credenciais renovadas"),
            @ApiResponse(responseCode = "401", description = "Credencial de refresh inválida")
    })
    @PostMapping("/refresh")
    ResponseEntity<Void> refresh();
}
