package in.reqres.models.register;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class RegisterRequestBodyModel {

    String email;
    @JsonIgnoreProperties(ignoreUnknown = true)
    String password;
}
