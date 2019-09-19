package com.codacy.CommitViewer;

import com.codacy.CommitViewer.git.CommitsDao;
import com.codacy.CommitViewer.git.CommitsService;
import com.codacy.CommitViewer.git.CommitsServiceImpl;
import com.codacy.CommitViewer.model.Commit;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

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
}
