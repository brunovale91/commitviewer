package com.codacy.CommitViewer.git;

import com.codacy.CommitViewer.model.Commit;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalCommitsDao implements CommitsDao {

    private static final String DIR = "repos";

    @Override
    public List<Commit> getCommits(String owner, String repo, int page) throws IOException, GitAPIException {
        Repository gitRepo = new FileRepositoryBuilder()
                .setGitDir(new File(DIR + "/" + owner + "/" + repo +"/.git"))
                .build();
        LogCommand logCommand = new Git(gitRepo).log();
        logCommand.setMaxCount(CommitsDao.PAGE_SIZE);
        logCommand.setSkip((page - 1) * PAGE_SIZE);
        List<Commit> commits = new ArrayList<>();
        for (RevCommit revCommit : logCommand.call()) {
            addCommit(commits, revCommit);
        }

        return commits;
    }

    private void addCommit(List<Commit> commits, RevCommit revCommit) {
        String id = revCommit.getId().getName();
        String authorEmail = revCommit.getAuthorIdent().getEmailAddress();
        int time = revCommit.getCommitTime();
        String shortMessage = revCommit.getShortMessage();
        List<String> parents = new ArrayList<>();
        for(RevCommit parent : revCommit.getParents()) {
            parents.add(parent.getId().name());
        }
        commits.add(new Commit(id, time, authorEmail, shortMessage, parents));
    }
}
