package cocus.githubclient.service.impl;

import cocus.githubclient.apiclient.GitHubApiClient;
import cocus.githubclient.dto.RepositoryBranchDto;
import cocus.githubclient.dto.RepositoryDto;
import cocus.githubclient.service.GitHubRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GitHubRepositoryServiceImpl implements GitHubRepositoryService {

    private final GitHubApiClient gitHubApiClient;

    @Autowired
    public GitHubRepositoryServiceImpl(GitHubApiClient gitHubApiClient) {
        this.gitHubApiClient = gitHubApiClient;
    }

    @Override
    public Flux<RepositoryDto> listUserRepositories(String username) {
        return gitHubApiClient.listUserRepositories(username).filter(repo -> !repo.isFork()).flatMap(repo -> {
            RepositoryDto repository = new RepositoryDto();
            repository.setName(repo.getName());
            repository.setOwnerLogin(repo.getOwner().getLogin());
            return Mono.just(repository);
        }).flatMap(repository -> getBranches(username, repository.getName()).collectList().flatMap(branches -> {
            repository.setBranches(branches);
            return Mono.just(repository);
        }));
    }

    private Flux<RepositoryBranchDto> getBranches(String username, String repository) {
        return gitHubApiClient.listBranches(username, repository).flatMap(br -> {
            RepositoryBranchDto branch = new RepositoryBranchDto();
            branch.setName(br.getName());
            branch.setLastCommitSha(br.getCommit().getSha());
            return Mono.just(branch);
        });
    }
}