package Test.Test.dao;

import Test.Test.dto.DetailUserDTO;
import Test.Test.entity.DetailUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DetailUsersRepository extends JpaRepository<DetailUsers, Long> {
    @Query("""
            SELECT new Test.Test.dto.DetailUserDTO(usdet.firstName, usdet.lastName)
            FROM DetailUsers AS usdet
            WHERE usdet.userId = :userId AND usdet.deleteAt IS NULL
            """)
    public DetailUserDTO getUserDetail(@Param("userId") Long id);

    @Query("""
            SELECT COUNT(*)
            FROM DetailUsers AS usdet
            WHERE usdet.userId = :userId AND usdet.deleteAt IS NULL
            """)
    public Long countDetailUserByUserId(@Param("userId") Long userId);
}
