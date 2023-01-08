package cocus.githubclient.apiclient.apimodel;

import lombok.Data;

@Data
public class GitHubRepositoryBranch {
    private String name;
    private GitHubRepositoryBranchCommit commit;
}
