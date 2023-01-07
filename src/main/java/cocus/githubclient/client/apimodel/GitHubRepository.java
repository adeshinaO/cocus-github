package cocus.githubclient.client.apimodel;

import lombok.Data;

@Data
public class GitHubRepository {
    private String name;
    private GitHubRepositoryOwner owner;
}
