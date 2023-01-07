package cocus.githubclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RepositoryBranchDto {

    @JsonProperty("branch_name")
    private String name;

    @JsonProperty("last_commit_SHA")
    private String lastCommitSha;
}
