package org.example.reposlistingspring.assemblers;

import org.example.reposlistingspring.DTO.Branch;
import org.example.reposlistingspring.DTO.GetUserReposResponse;
import org.example.reposlistingspring.DTO.Repo;

import java.util.List;
import java.util.stream.Collectors;

public class UserReposAssembler {
    public static GetUserReposResponse.UserRepo toUserRepo(Repo repo, List<Branch> branches) {
        return new GetUserReposResponse.UserRepo(
                repo.name(),
                repo.owner().login(),
                branches.stream().map(b -> new GetUserReposResponse.Branch(b.name(), b.commit().sha()))
                        .collect(Collectors.toList())
        );
    }
}
