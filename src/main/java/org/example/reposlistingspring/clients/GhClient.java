package org.example.reposlistingspring.clients;

import org.example.reposlistingspring.DTO.Branch;
import org.example.reposlistingspring.DTO.Repo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class GhClient {
    private final RestClient restClient;


    public GhClient(RestClient.Builder restClientBuilder, @Value("${github.api.base}") String baseUrl) {
        this.restClient = restClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

    public List<Repo> getUserPublicRepos(String username){
        return restClient.get()
                .uri("users/{username}/repos", username)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Repo>>() {});
    }

    public List<Branch> getReposBranches(String username, String repositoryName){
        return restClient.get()
                .uri("repos/{username}/{repositoryName}/branches",username,repositoryName)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Branch>>() {});
    }
}
