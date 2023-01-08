package cocus.githubclient.apiclient;

import cocus.githubclient.apiclient.apimodel.GitHubRepository;
import cocus.githubclient.apiclient.apimodel.GitHubRepositoryBranch;
import reactor.core.publisher.Flux;

/**
 * Base interface for classes that make requests to GitHub's API.
 */
public interface GitHubClient {
    /**
     * Returns a list of repositories owned by the user with the given username.
     */
    Flux<GitHubRepository> listUserRepositories(String username);
    Flux<GitHubRepositoryBranch> listBranches(String username, String repository);
}
