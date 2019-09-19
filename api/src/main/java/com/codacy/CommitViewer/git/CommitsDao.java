package com.codacy.CommitViewer.git;

import com.codacy.CommitViewer.model.Commit;

import java.util.List;

public interface CommitsDao {

    int PAGE_SIZE = 10;

    List<Commit> getCommits(String owner, String repo, int page) throws Exception;

}
