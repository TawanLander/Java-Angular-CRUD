package lander.tawan.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    private UUID id;

    @Column(nullable = false)
    @Getter @Setter
    private String name;

    @Column(unique = true, nullable = false)
    @Getter @Setter
    private String phone;

    @Column(unique = true, nullable = false)
    @Getter @Setter
    private String email;

    @Column(name = "created_at", nullable = false)
    @Getter
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    @Getter @Setter
    private LocalDateTime deletedAt;

    protected UserEntity(){};

    public UserEntity(String name, String phone, String email){
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.deletedAt = null;
    }

    public boolean isDeleted(){
        return this.deletedAt != null;
    }
}