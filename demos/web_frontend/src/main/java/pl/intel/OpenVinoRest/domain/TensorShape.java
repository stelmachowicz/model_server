package pl.intel.OpenVinoRest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
public class TensorShape {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="tensorshape_id", nullable=false)
    private List<Dim> dim;
    private Boolean unknownRank;
}
