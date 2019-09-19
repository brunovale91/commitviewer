package com.codacy.CommitViewer.git;

import com.codacy.CommitViewer.model.Commit;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommitsServiceImpl implements CommitsService {

    private CommitsDao localCommitsDao;
    private CommitsDao githubCommitsDao;


    public CommitsServiceImpl() {
        this.localCommitsDao = new LocalCommitsDao();
        this.githubCommitsDao = new GithubCommitsDao();
    }

    @Override
    public List<Commit> getCommits(String owner, String repo, int page) throws Exception {
        try {
            return githubCommitsDao.getCommits(owner, repo, page);
        } catch (Exception e) {
            return localCommitsDao.getCommits(owner, repo, page);
        }
    }
}
