package cocus.githubclient.service;

import cocus.githubclient.dto.RepositoryDto;
import reactor.core.publisher.Flux;

public interface GitHubRepositoryService {
    Flux<RepositoryDto> listUserRepositories(String username);
}
