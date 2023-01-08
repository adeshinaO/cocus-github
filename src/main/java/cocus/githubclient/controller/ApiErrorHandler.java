package cocus.githubclient.controller;

import cocus.githubclient.dto.ErrorDto;
import cocus.githubclient.exception.InvalidGitHubUserException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiErrorHandler {

    @ExceptionHandler
    public ErrorDto handleWebClientException(InvalidGitHubUserException exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(exception.getMessage());
        errorDto.setStatus(404);
        return errorDto;
    }
}
