package cocus.githubclient.controller;

import static org.mockito.Mockito.times;

import cocus.githubclient.apiclient.GitHubClient;
import cocus.githubclient.apiclient.apimodel.GitHubRepository;
import cocus.githubclient.apiclient.apimodel.GitHubRepositoryBranch;
import cocus.githubclient.apiclient.apimodel.GitHubRepositoryBranchCommit;
import cocus.githubclient.apiclient.apimodel.GitHubRepositoryOwner;
import cocus.githubclient.exception.InvalidGitHubUserException;
import cocus.githubclient.service.GitHubService;
import cocus.githubclient.service.impl.GitHubServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

@WebFluxTest(controllers = {GitHubController.class})
@Import({GitHubServiceImpl.class})
public class GitHubControllerTest {

    private final static String GITHUB_USERNAME = "adeshinaO";
    private final static String REPOSITORY_NAME = "my-repo";

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GitHubClient gitHubClient;

    @Autowired
    private GitHubService gitHubService;

    @Test
    @DisplayName("Should return 200 for valid GitHub Username")
    public void getRepositoriesForRealUsername() throws Exception {

        Mockito.when(gitHubClient.listUserRepositories(GITHUB_USERNAME))
               .thenReturn(Flux.just(mockRepos()));

        Mockito.when(gitHubClient.listBranches(GITHUB_USERNAME, REPOSITORY_NAME))
               .thenReturn(Flux.just(mockBranch()));

        webTestClient.get()
                     .uri(String.format("/%s/repositories", GITHUB_USERNAME))
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus()
                     .isOk();

        Mockito.verify(gitHubClient, times(1)).listUserRepositories(GITHUB_USERNAME);
        Mockito.verify(gitHubClient, times(1)).listBranches(GITHUB_USERNAME, REPOSITORY_NAME);
    }

    @Test
    @DisplayName("Should return 404 for invalid GitHub Username")
    public void throwErrorForUsername() throws Exception {

        Mockito.when(gitHubClient.listUserRepositories(GITHUB_USERNAME))
               .thenThrow(InvalidGitHubUserException.class);

        webTestClient.get()
                     .uri(String.format("/%s/repositories", GITHUB_USERNAME))
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus()
                     .isNotFound();

        Mockito.verify(gitHubClient, times(1)).listUserRepositories(GITHUB_USERNAME);
    }

    @Test
    @DisplayName("Should return 406 for unsupported ACCEPT header value")
    public void throwErrorForWrongContentType() throws Exception {
        webTestClient.get()
                     .uri(String.format("/%s/repositories", GITHUB_USERNAME))
                     .accept(MediaType.APPLICATION_XML)
                     .exchange()
                     .expectStatus()
                     .isEqualTo(HttpStatus.NOT_ACCEPTABLE);
    }

    private GitHubRepository[] mockRepos() {

        GitHubRepositoryOwner owner = new GitHubRepositoryOwner();
        GitHubRepositoryOwner owner2 = new GitHubRepositoryOwner();
        owner.setLogin("my-username");
        owner2.setLogin("another-username");

        GitHubRepository repository = new GitHubRepository();
        repository.setName(REPOSITORY_NAME);
        repository.setFork(false);
        repository.setOwner(owner);

        GitHubRepository forkedRepository = new GitHubRepository();
        forkedRepository.setName("not-forked");
        forkedRepository.setFork(true);
        forkedRepository.setOwner(owner2);

        return new GitHubRepository[] {forkedRepository, repository};
    }

    private GitHubRepositoryBranch mockBranch() {
        GitHubRepositoryBranchCommit commit = new GitHubRepositoryBranchCommit();
        commit.setSha("3229n2oju3e");
        GitHubRepositoryBranch branch = new GitHubRepositoryBranch();
        branch.setName("some-branch");
        branch.setCommit(commit);
        return branch;
    }
}
