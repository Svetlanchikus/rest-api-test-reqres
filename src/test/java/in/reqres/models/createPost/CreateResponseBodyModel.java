package in.reqres.models.createPost;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class CreateResponseBodyModel {

    String name;
    String job;
    @JsonIgnoreProperties(ignoreUnknown = true)
    Integer id;
    @JsonIgnoreProperties(ignoreUnknown = true)
    String createdAt;
}
