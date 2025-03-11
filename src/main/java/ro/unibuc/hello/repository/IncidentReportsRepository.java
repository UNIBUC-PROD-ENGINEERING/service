package ro.unibuc.hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.entity.IncidentReportEntity;

@Repository
public interface IncidentReportsRepository extends JpaRepository<IncidentReportEntity, Long> {

}
