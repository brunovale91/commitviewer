package com.codacy.CommitViewer.controllers;

import com.codacy.CommitViewer.git.CommitsService;
import com.codacy.CommitViewer.model.Commit;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommitListController {

    private CommitsService commitsService;

    public CommitListController(CommitsService commitsService) {
        this.commitsService = commitsService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("{owner}/{repo}/commits")
    public List<Commit> getCommits(@PathVariable("owner") String owner, @PathVariable("repo") String repo, @RequestParam(name = "page", defaultValue = "1") int page) throws Exception {
        return commitsService.getCommits(owner, repo, page);
    }
}
