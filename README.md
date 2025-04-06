# Repos-listing-spring

Repos Listing is Spring application that retrieves user's public repositories (excluding forks) with their branches.

User passes GitHub username to endpoint
```http request
GET /{username}
```
The response is JSON object in following format:
```json
{
  "userRepos": [
    {
      "repositoryName": "Repository Name",
      "owner": "Owner Login",
      "branches": [
        {
          "name": "Branch Name",
          "lastCommitSha": "sha"
        }
      ]
    }
  ]
}
```

## Building

In order to build project use:

```bash
mvn clean package
```
## Running

In order to run use:

```bash
java -jar target/repos-listing-spring-0.0.1-SNAPSHOT.jar
```



