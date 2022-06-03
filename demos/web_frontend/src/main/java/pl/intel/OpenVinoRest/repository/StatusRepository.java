package pl.intel.OpenVinoRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.intel.OpenVinoRest.domain.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
}
