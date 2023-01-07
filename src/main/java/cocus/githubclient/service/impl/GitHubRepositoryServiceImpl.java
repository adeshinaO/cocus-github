package cocus.githubclient.service.impl;

import cocus.githubclient.client.GitHubApiClient;
import cocus.githubclient.dto.RepositoryBranchDto;
import cocus.githubclient.dto.RepositoryDto;
import cocus.githubclient.service.GitHubRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class GitHubRepositoryServiceImpl implements GitHubRepositoryService {

    private final GitHubApiClient gitHubApiClient;

    @Autowired
    public GitHubRepositoryServiceImpl(GitHubApiClient gitHubApiClient) {
        this.gitHubApiClient = gitHubApiClient;
    }

    @Override
    public Flux<RepositoryDto> listUserRepositories(String username) {
        return gitHubApiClient.listUserRepositories(username).map(repo -> {
            RepositoryDto repository = new RepositoryDto();
            repository.setName(repo.getName());
            repository.setOwnerLogin(repo.getOwner().getLogin());

            Flux<RepositoryBranchDto> branchDtoFlux = gitHubApiClient.listBranches(username, repository.getName()).map(br -> {
                RepositoryBranchDto branch = new RepositoryBranchDto();
                branch.setName(br.getName());
                branch.setLastCommitSha(br.getCommit().getSha());
                return branch;
            });

            branchDtoFlux.collectList().doOnNext(repository::setBranches).subscribe();
            return repository;
        });
    }
}
