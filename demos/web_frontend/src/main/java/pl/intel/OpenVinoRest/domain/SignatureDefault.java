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
public class SignatureDefault {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    //@Column(name = "@type")
    //private String type;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="signatureDef_id")
    private SignatureDef signatureDef;

}
