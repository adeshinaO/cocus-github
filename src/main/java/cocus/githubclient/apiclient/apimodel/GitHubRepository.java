package cocus.githubclient.apiclient.apimodel;

import lombok.Data;

@Data
public class GitHubRepository {
    private String name;
    private GitHubRepositoryOwner owner;
    private boolean fork;
}
