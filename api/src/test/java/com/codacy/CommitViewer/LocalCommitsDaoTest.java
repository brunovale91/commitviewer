package com.codacy.CommitViewer;

import com.codacy.CommitViewer.git.CommitsDao;
import com.codacy.CommitViewer.git.LocalCommitsDao;
import com.codacy.CommitViewer.model.Commit;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LocalCommitsDaoTest {

    @Test
    public void testLocalCommitsList() throws Exception {
        CommitsDao commitsDao = new LocalCommitsDao();
        List<Commit> commitList = commitsDao.getCommits("swarmbit", "swarmmanager", 1);
        assertEquals(10, commitList.size());

        Commit firstCommit = commitList.get(0);
        assertEquals("d03d9fd1ba36a5dc40f55d886820b6c224cdd240", firstCommit.getId());
        deleteRepo();
    }

    @Before
    public void createSwarmmanagerRepo() throws GitAPIException, IOException {
        deleteRepo();
        Git.cloneRepository()
                .setURI("https://github.com/swarmbit/swarmmanager.git")
                .setDirectory(Paths.get("repos/swarmbit/swarmmanager").toFile())
                .call();
    }

    private void deleteRepo() throws IOException {
        FileUtils.deleteDirectory(new File("repos/swarmbit"));
    }

}
