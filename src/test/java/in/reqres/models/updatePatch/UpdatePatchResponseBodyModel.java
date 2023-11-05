package in.reqres.models.updatePatch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class UpdatePatchResponseBodyModel {

    String name;
    String email;
    @JsonIgnoreProperties(ignoreUnknown = true)
    String updatedAt;
}
