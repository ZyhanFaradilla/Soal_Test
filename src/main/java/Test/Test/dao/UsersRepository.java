package Test.Test.dao;

import Test.Test.dto.Users.UserDataDetailDTO;
import Test.Test.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {
    @Query("""
            SELECT us.id
            FROM Users AS us
            WHERE us.deleteAt IS NULL AND us.username = :username
            """)
    public Long getIdUser(@Param("username") String username);

    @Query("""
            SELECT new Test.Test.dto.Users.UserDataDetailDTO(user.id, user.username, user.createdBy, 
            user.createdAt, user.updateBy, user.updateAt, user.deleteBy, user.deleteAt)
            FROM Users AS user
            """)
    public List<UserDataDetailDTO> getAllUser();

    @Query("""
            SELECT COUNT(us.id)
            FROM Users AS us
            WHERE us.username = :username
            """)
    public Integer countExistUsername(@Param("username") String username);
}
