package cocus.githubclient.client.impl;

import cocus.githubclient.client.GitHubApiClient;
import cocus.githubclient.client.apimodel.GitHubRepository;
import cocus.githubclient.client.apimodel.GitHubRepositoryBranch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class GitHubApiClientImpl implements GitHubApiClient {

    @Value("${github.base_url:https://api.github.com}")
    private String baseUrl;

    private static final String REPOSITORIES_URL_TEMPLATE = "";
    private static final String BRANCHES_URL_TEMPLATE = "";

    private final WebClient webClient;


    @Autowired
    public GitHubApiClientImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
    public Flux<GitHubRepository> listUserRepositories(String username) {


        return null;
    }

    @Override
    public Flux<GitHubRepositoryBranch> listBranches(String username, String repository) {


        return null;
    }
}
