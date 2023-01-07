package cocus.githubclient.client.apimodel;

import lombok.Data;

@Data
public class GitHubRepositoryBranch {
    private String name;
    private GitHubRepositoryBranchCommit commit;
}
