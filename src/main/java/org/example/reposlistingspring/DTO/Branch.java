package org.example.reposlistingspring.DTO;

public record Branch(String name, Commit commit) {
    public record Commit(String sha){}
}
