package lander.tawan.user.service;

import lander.tawan.enums.paramStatus.ParamStatus;
import lander.tawan.user.dto.UserDataResponse;
import lander.tawan.user.dto.UserPatchRequest;
import lander.tawan.user.dto.UserPutRequest;
import lander.tawan.user.dto.UserRegisterRequest;
import lander.tawan.user.entity.UserEntity;
import lander.tawan.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<UserDataResponse> listUsers(ParamStatus status){
        List<UserEntity> result = switch(status){
            case ACTIVE -> userRepository.findAllByDeletedAtIsNull();
            case DELETED -> userRepository.findAllByDeletedAtIsNotNull();
            case ALL -> userRepository.findAll();
        };

        return result.stream().map(UserDataResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public UserDataResponse getUser(UUID id){
        return UserDataResponse.from(findById(id));
    }

    @Transactional
    public UserDataResponse registerUser(UserRegisterRequest req){
        validateUniqueCredentials(req.email(), req.phone());
        UserEntity user = new UserEntity(
                req.name(),
                req.phone(),
                req.email()
        );

        userRepository.save(user);
        return UserDataResponse.from(user);
    }

    @Transactional
    public void patchUser(UUID id, UserPatchRequest req){
        UserEntity user = findActiveUserById(id);
        validateUniqueCredentialsForUpdate(id, req.email(), req.phone());
        if(req.name() != null) user.setName(req.name());
        if(req.email() != null) user.setEmail(req.email());
        if(req.phone() != null) user.setPhone(req.phone());
    }

    @Transactional
    public void putUser(UUID id, UserPutRequest req){
        UserEntity user = findActiveUserById(id);
        validateUniqueCredentialsForUpdate(id, req.email(), req.phone());
        user.setName(req.name());
        user.setPhone(req.phone());
        user.setEmail(req.email());
    }

    @Transactional
    public void deleteUser(UUID id){
        UserEntity user = findActiveUserById(id);
        user.setDeletedAt(LocalDateTime.now());
    }

    @Transactional
    public void restoreUser(UUID id){
        UserEntity user = findById(id);
        if(!user.isDeleted()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Usuário não está excluído");
        }
        user.setDeletedAt(null);
    }

    @Transactional
    public void hardDeleteUser(UUID id){
        UserEntity user = findById(id);
        if(!user.isDeleted()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Usuário deve ser desativado primeiro");
        }
        userRepository.deleteById(id);
    }


    private UserEntity findById(UUID id){
        return userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado")
        );
    }

    private UserEntity findActiveUserById(UUID id){
        return userRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado")
        );
    }

    private void validateUniqueCredentials(String email, String phone){
        if(userRepository.existsByEmail(email) || userRepository.existsByPhone(phone)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Verifique as credênciais e tente novamente");
        }
    }

    private void validateUniqueCredentialsForUpdate(UUID id, String email, String phone){
        boolean emailTaken = email != null && userRepository.existsByEmailAndIdNot(email, id);
        boolean phoneTaken = phone != null && userRepository.existsByPhoneAndIdNot(phone, id);

        if(emailTaken || phoneTaken){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Verifique as credênciais e tente novamente");
        }
    }
}
