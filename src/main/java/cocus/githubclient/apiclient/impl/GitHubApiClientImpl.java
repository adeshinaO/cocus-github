package cocus.githubclient.apiclient.impl;

import cocus.githubclient.apiclient.GitHubApiClient;
import cocus.githubclient.apiclient.apimodel.GitHubRepository;
import cocus.githubclient.apiclient.apimodel.GitHubRepositoryBranch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class GitHubApiClientImpl implements GitHubApiClient {

    @Value("${github.base_url:https://api.github.com}")
    private String baseUrl;

    private static final String REPOSITORIES_URL_TEMPLATE = "/users/%s/repos?type=owner";
    private static final String BRANCHES_URL_TEMPLATE = "/repos/%s/%s/branches";

    private final WebClient webClient;

    @Autowired
    public GitHubApiClientImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github+json")
                                         .baseUrl(baseUrl)
                                         .build();
    }

    @Override
    public Flux<GitHubRepository> listUserRepositories(String username) {
        return webClient.get()
                        .uri(String.format(REPOSITORIES_URL_TEMPLATE, username))
                        .retrieve()
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