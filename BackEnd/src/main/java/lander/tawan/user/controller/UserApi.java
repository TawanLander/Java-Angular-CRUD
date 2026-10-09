package lander.tawan.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lander.tawan.enums.paramStatus.ParamStatus;
import lander.tawan.user.dto.UserDataResponse;
import lander.tawan.user.dto.UserPatchRequest;
import lander.tawan.user.dto.UserPutRequest;
import lander.tawan.user.dto.UserRegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Users", description = "Gerenciamento de usuários")
@RequestMapping("/users")
public interface UserApi {

    @Operation(summary = "Traz uma lista completa dos usuários")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuários encontrados")
    })
    @GetMapping
    List<UserDataResponse> listUsers(
            @Parameter(description = "Estado dos usuários: ativos (padrão), excluídos (soft delete) ou todos")
            @RequestParam(name = "status", defaultValue = "ACTIVE") ParamStatus status
    );

    @Operation(summary = "Buscar usuário por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    UserDataResponse getUser(@PathVariable UUID id);

    @Operation(summary = "Cadastrar novo usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "Email ou telefone já cadastrados", content = @Content)
    })
    @PostMapping
    ResponseEntity<UserDataResponse> registerUser(@Valid @RequestBody UserRegisterRequest req);

    @Operation(summary = "Atualiza o usuário pelo id parcialmente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário atualizado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado ou excluído"),
            @ApiResponse(responseCode = "409", description = "E-mail ou telefone já cadastrado")
    })
    @PatchMapping("/{id}")
    ResponseEntity<Void> patchUser(@PathVariable UUID id, @Valid @RequestBody UserPatchRequest req);

    @Operation(summary = "Atualiza o usuário pelo id completamente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário atualizado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado ou excluído"),
            @ApiResponse(responseCode = "409", description = "E-mail ou telefone já cadastrado")
    })
    @PutMapping("/{id}")
    ResponseEntity<Void> putUser(@PathVariable UUID id, @Valid @RequestBody UserPutRequest req);

    @Operation(summary = "Delete o usuário pelo id (SoftDelete)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário deletado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable UUID id);

    @Operation(summary = "Restaura o usuário deletado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário restaurado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Usuário não está excluído")
    })
    @PatchMapping("/{id}/restore")
    ResponseEntity<Void> restoreUser(@PathVariable UUID id);

    @Operation(summary = "Deleta o usuário pelo id permanentemente (HardDelete)",
            description = "Só funciona para usuário que já tenham sofrido SoftDelete. Retorna 409 caso contrário.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário deletado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Usuário não está excluído")
    })
    @DeleteMapping("/{id}/permanent")
    ResponseEntity<Void> hardDeleteUser(@PathVariable UUID id);
}
