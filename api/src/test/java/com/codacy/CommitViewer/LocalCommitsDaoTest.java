package com.codacy.CommitViewer;

import com.codacy.CommitViewer.git.CommitsDao;
import com.codacy.CommitViewer.git.LocalCommitsDao;
import com.codacy.CommitViewer.model.Commit;
import org.junit.Test;

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
    }

}
