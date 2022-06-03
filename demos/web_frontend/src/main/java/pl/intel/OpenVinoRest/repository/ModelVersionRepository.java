package pl.intel.OpenVinoRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.intel.OpenVinoRest.domain.ModelVersion;

@Repository
public interface ModelVersionRepository extends JpaRepository<ModelVersion, Long> {
}
