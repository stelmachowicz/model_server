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
public class MetadataInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="modelSpec_id")
    private ModelSpec modelSpec;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="metadata_id")
    private Metadata metadata;
}
