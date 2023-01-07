package cocus.githubclient.client;

import cocus.githubclient.client.apimodel.GitHubRepository;
import cocus.githubclient.client.apimodel.GitHubRepositoryBranch;
import java.util.Optional;
import reactor.core.publisher.Flux;

/**
 * Base interface for classes that make requests to GitHub's API.
 */
public interface GitHubApiClient {
    /**
     * Returns a list of repositories owned by the user with the given username.
     */
    Flux<GitHubRepository> listUserRepositories(String username);
    Flux<GitHubRepositoryBranch> listBranches(String username, String repository);
}
