package cocus.githubclient.dto;

import lombok.Data;

@Data
public class ErrorDto {
    private int status;
    private String message;
}
