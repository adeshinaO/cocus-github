package cocus.githubclient.apiclient.impl;

import cocus.githubclient.apiclient.GitHubClient;
import cocus.githubclient.apiclient.apimodel.GitHubRepository;
import cocus.githubclient.apiclient.apimodel.GitHubRepositoryBranch;
import cocus.githubclient.exception.InvalidGitHubUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class GitHubClientImpl implements GitHubClient {

    private static final String REPOS_URL_TEMPLATE = "https://api.github.com/users/%s/repos?type=owner&per_page=100";
    private static final String BRANCHES_URL_TEMPLATE = "https://api.github.com/repos/%s/%s/branches";
    private static final String INVALID_USERNAME_TEMPLATE = "The username %s does not exist on GitHub";

    private final WebClient webClient;

    @Autowired
    public GitHubClientImpl(
            WebClient.Builder webClientBuilder,
            @Value("${github.base_url:https://api.github.com}") String baseUrl) {
        this.webClient = webClientBuilder.defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github+json")
                                         .baseUrl(baseUrl)
                                         .build();
    }

    @Override
    public Flux<GitHubRepository> listUserRepositories(String username) {
        return webClient.get()
                        .uri(String.format(REPOS_URL_TEMPLATE, username))
                        .retrieve()
                        .onStatus(HttpStatus.NOT_FOUND::equals,
                                clientResponse ->
                                        clientResponse.toBodilessEntity()
                                                      .map(s -> new InvalidGitHubUserException(String.format(INVALID_USERNAME_TEMPLATE, username))))
                        .bodyToFlux(GitHubRepository.class);
    }

    @Override
    public Flux<GitHubRepositoryBranch> listBranches(String username, String repository) {
        return webClient.get()
                        .uri(String.format(BRANCHES_URL_TEMPLATE, username, repository))
                        .retrieve()
                        .bodyToFlux(GitHubRepositoryBranch.class);
    }
}
