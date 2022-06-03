package pl.intel.OpenVinoRest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
public class ModelVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    //@Column(name = "model_version_status")
    @OneToMany(mappedBy = "status")
    private List<ModelVersionStatus> model_version_status;

    public ModelVersion(List<ModelVersionStatus> versionStatuses) {
        this.model_version_status = versionStatuses;

    }
}
