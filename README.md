## Overview

A web service that exposes an endpoint for retrieving the repositories of a GitHub user.
It fetches the user's repos, filters out the forks, and returns a representation that includes the 
last commit SHA of each branch of each repository.

The service is implemented using Spring's reactive stack. This follows async programming paradigm and promises
a more efficient use of system resources than the servlet stack.

#### Technology Stack

- Java 11
- Maven
- Spring WebFlux
- Docker

#### How to Deploy Locally

Open a terminal at the project root and do the following:

- Run all tests and build an executable JAR
```shell
    ./mvnw clean package 
```

- Simplest way to run the app is to use the Maven Spring Boot plugin
```shell
    ./mvnw spring-boot:run
```

This starts up the app and binds it to port 8080.


- Create an image using the Dockerfile in the project root
```shell
    docker build --tag=github-service:latest .
```

- Start a Docker container with port 8080 exposed
```shell
    docker run -p8080:8080 github-service:latest
```

## API 

- GET `/{github_username}/repositories`

Success Response Sample

```json
[
  {
	"branches": [
		{
			"branch_name": "master",
			"last_commit_SHA": "5919b5eccd78f97d091c6066afc24b65296bcdc8"
		}
	],
	"repository_name": "apache-kafka-demo",
	"owner_login": "adeshinaO"
},
{
	"branches": [
		{
			"branch_name": "master",
			"last_commit_SHA": "96236bd6ad759402b035ce3507b5a43b1fdf8444"
		}
	],
	"repository_name": "cocus-github",
	"owner_login": "adeshinaO"
}
]
```

Error Response Sample

```json
{
	"status": 404,
	"message": "The username m4c333333s does not exist on GitHub"
}
```