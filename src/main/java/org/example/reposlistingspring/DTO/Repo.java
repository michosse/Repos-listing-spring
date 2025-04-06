package org.example.reposlistingspring.DTO;

public record Repo(String name, Owner owner, boolean fork) {
    public record Owner(String login){}
}
