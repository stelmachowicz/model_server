package pl.intel.OpenVinoRest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    //@Column(name = "error_code")
    private String error_code;
    //@Column(name = "error_message")
    private String error_message;

    public Status(String errorCode, String errorMessage) {
        this.error_code = errorCode;
        this.error_message = errorMessage;
    }
}
