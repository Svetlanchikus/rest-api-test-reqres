package in.reqres.models.listusersget;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ListUsersResponseBodyModel {
    int page;
    @JsonProperty("per_page")
    int perPage;
    int total;
    @JsonProperty("total_pages")
    int totalPages;
    List<ListUsersDataResponseBodyModel> data;
    ListUsersSupportResponseBodyModel support;
}
