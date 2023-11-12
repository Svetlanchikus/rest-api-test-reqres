package in.reqres.models.listusersget;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data

public class ListUsersDataResponseBodyModel {
    int id;
    String email;
    @JsonProperty("first_name")
    String firstName;
    @JsonProperty("last_name")
    String lastName;
    String avatar;
}
