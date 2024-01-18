package Test.Test.dao;

import Test.Test.dto.JobDTO;
import Test.Test.entity.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobsRepository extends JpaRepository<Jobs, Long> {
    @Query("""
            SELECT new Test.Test.dto.JobDTO(job.name, job.startAt, job.endAt)
            FROM Jobs AS job
            WHERE job.userId = :userId
            """)
    public List<JobDTO> getJob(@Param("userId") Long id);
}
