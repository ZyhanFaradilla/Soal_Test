package Test.Test.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String password;

    @Column(name = "Created_by")
    private Long createdBy;

    @Column(name = "Created_at")
    private LocalDateTime createdAt;

    @Column(name = "Update_by")
    private Long updateBy;

    @Column(name = "Update_at")
    private LocalDateTime updateAt;

    @Column(name = "Delete_by")
    private Long deleteBy;

    @Column(name = "Delete_at")
    private LocalDateTime deleteAt;
}
