package org.example.reposlistingspring.DTO;

import java.util.List;

public record GetUserReposResponse(List<UserRepo> userRepos) {
    public record Branch(String name, String lastCommitSha){}

    public record UserRepo(String repositoryName, String owner, List<Branch> branches){}
}
