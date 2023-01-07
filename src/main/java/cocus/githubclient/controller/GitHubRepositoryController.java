package cocus.githubclient.controller;

import cocus.githubclient.dto.RepositoryDto;
import cocus.githubclient.service.GitHubRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class GitHubRepositoryController {

    private final GitHubRepositoryService service;

    @Autowired
    public GitHubRepositoryController(GitHubRepositoryService service) {
        this.service = service;
    }

    @GetMapping(value = "/{user}/repositories", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<RepositoryDto> listRepositories(@PathVariable("user") String username) {
        return service.listUserRepositories(username);
    }
}
