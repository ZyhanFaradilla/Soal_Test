package Test.Test.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Jobs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Jobs {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "User_Id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "User_Id", insertable = false, updatable = false)
    private Users users;

    @Column(name = "Name")
    private String name;

    @Column(name = "Start_at")
    private LocalDate startAt;

    @Column(name = "End_at")
    private LocalDate endAt;

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
