package pl.intel.OpenVinoRest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "model_version_status")
public class ModelVersionStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String version;
    private String state;
    @ManyToOne
    private Status status;

    public ModelVersionStatus(String version, String state, Status status) {
        this.version = version;
        this.state = state;
        this.status = status;
    }
}
