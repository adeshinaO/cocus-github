package cocus.githubclient.controller;

import cocus.githubclient.dto.ErrorDto;
import cocus.githubclient.exception.InvalidGitHubUserException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class ApiErrorHandler {

    @ExceptionHandler
    public Mono<ErrorDto> handleWebClientException(InvalidGitHubUserException exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(exception.getMessage());
        errorDto.setStatus(404);
        return Mono.just(errorDto);
    }
}
