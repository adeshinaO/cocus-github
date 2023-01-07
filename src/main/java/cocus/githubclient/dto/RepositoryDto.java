package cocus.githubclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class RepositoryDto {

    @JsonProperty("repository_name")
    private String name;

    @JsonProperty("owner_login")
    private String ownerLogin;
    private List<RepositoryBranchDto> branches;
}
