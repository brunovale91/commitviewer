package com.codacy.CommitViewer.git;

import com.codacy.CommitViewer.model.Commit;

import java.util.List;

public interface CommitsService {

    List<Commit> getCommits(String owner, String repo, int page) throws Exception;

}
