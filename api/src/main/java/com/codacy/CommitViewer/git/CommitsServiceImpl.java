package com.codacy.CommitViewer.git;

import com.codacy.CommitViewer.model.Commit;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommitsServiceImpl implements CommitsService {

    private static final Logger LOGGER = Logger.getLogger(CommitsServiceImpl.class);
    private CommitsDao localCommitsDao;
    private CommitsDao githubCommitsDao;


    public CommitsServiceImpl() {
        this.localCommitsDao = new LocalCommitsDao();
        this.githubCommitsDao = new GithubCommitsDao();
    }

    @Override
    public List<Commit> getCommits(String owner, String repo, int page) throws Exception {
        try {
            LOGGER.info("Get commits from github: owner - " + owner + ", repo - " + repo);
            return githubCommitsDao.getCommits(owner, repo, page);
        } catch (Exception e) {
            LOGGER.error(e);
            return localCommitsDao.getCommits(owner, repo, page);
        }
    }
}
