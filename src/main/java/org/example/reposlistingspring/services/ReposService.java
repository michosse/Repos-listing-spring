package org.example.reposlistingspring.services;

import org.example.reposlistingspring.DTO.Branch;
import org.example.reposlistingspring.DTO.GetUserReposResponse;
import org.example.reposlistingspring.DTO.Repo;
import org.example.reposlistingspring.assemblers.UserReposAssembler;
import org.example.reposlistingspring.clients.GhClient;
import org.example.reposlistingspring.exceptions.HttpException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReposService {
    private final GhClient ghClient;

    public ReposService(GhClient ghClient) {
        this.ghClient = ghClient;
    }

    public List<Repo> getUsersPublicRepos(String username) {
        try {
            return ghClient.getUserPublicRepos(username).stream().filter(repo -> !repo.fork()).collect(Collectors.toList());
        } catch (HttpClientErrorException e) {
            throw new HttpException(e.getStatusCode().value(), e.getStatusText());
        }
    }

    public List<Branch> getReposBranches(String username, String repoName) {
        return ghClient.getReposBranches(username, repoName);
    }

    public List<GetUserReposResponse.UserRepo> getUserReposWithBranches(String username) {
        return getUsersPublicRepos(username).stream().map(r -> {
            List<Branch> branches = getReposBranches(username, r.name());
            return UserReposAssembler.toUserRepo(r, branches);
        }).toList();
    }

}
