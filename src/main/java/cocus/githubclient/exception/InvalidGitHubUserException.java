package cocus.githubclient.exception;

public class InvalidGitHubUserException extends RuntimeException {
    public InvalidGitHubUserException(String message) {
        super(message);
    }
}
