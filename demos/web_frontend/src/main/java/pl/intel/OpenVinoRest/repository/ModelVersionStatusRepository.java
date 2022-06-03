package pl.intel.OpenVinoRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.intel.OpenVinoRest.domain.ModelVersionStatus;

@Repository
public interface ModelVersionStatusRepository extends JpaRepository<ModelVersionStatus, Long> {
}
