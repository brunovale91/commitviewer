package com.codacy.CommitViewer;

import com.codacy.CommitViewer.git.CommitsDao;
import com.codacy.CommitViewer.git.CommitsService;
import com.codacy.CommitViewer.git.CommitsServiceImpl;
import com.codacy.CommitViewer.model.Commit;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class CommitsServiceTest {

    @Test
    public void testCommitsListFallback() throws Exception {
        CommitsService commitsService = new CommitsServiceImpl();
        CommitsDao commitsDao = mock(CommitsDao.class);
        Whitebox.setInternalState(commitsService, "githubCommitsDao", commitsDao);
        when(commitsDao.getCommits(anyString(), anyString(), anyInt()))
                .thenThrow(Exception.class);

        List<Commit> commits = commitsService.getCommits("swarmbit", "swarmmanager", 1);
        assertEquals(10, commits.size());
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
