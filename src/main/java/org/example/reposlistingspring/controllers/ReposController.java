package org.example.reposlistingspring.controllers;

import org.example.reposlistingspring.DTO.GetUserReposResponse;
import org.example.reposlistingspring.services.ReposService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReposController {
    private final ReposService reposService;

    public ReposController(ReposService reposService) {
        this.reposService = reposService;
    }

    @GetMapping("/{username}")
    public GetUserReposResponse getRepos(@PathVariable("username") String username) {
        return new GetUserReposResponse(reposService.getUserReposWithBranches(username));
    }
}
