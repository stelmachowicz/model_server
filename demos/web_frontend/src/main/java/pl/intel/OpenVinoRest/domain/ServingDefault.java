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
public class ServingDefault {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="input_id")
    private Input inputs;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="output_id")
    private Output outputs;
    private String methodName;
}
