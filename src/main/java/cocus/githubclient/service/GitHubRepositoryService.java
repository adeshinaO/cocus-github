package cocus.githubclient.service;

import cocus.githubclient.dto.RepositoryDto;
import java.util.List;
import reactor.core.publisher.Flux;

public interface GitHubRepositoryService {
    Flux<RepositoryDto> listUserRepositories(String username);
}
