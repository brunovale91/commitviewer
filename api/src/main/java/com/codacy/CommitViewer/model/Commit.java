package com.codacy.CommitViewer.model;

import java.util.ArrayList;
import java.util.List;

public class Commit {

    private String id;

    private int time;

    private String message;

    private String authorEmail;

    private List<String> parents;

    public Commit(String id, int time, String message, String authorEmail, List<String> parents) {
        this.id = id;
        this.time = time;
        this.message = message;
        this.authorEmail = authorEmail;
        this.parents = parents;
    }

    public String getId() {
        return id;
    }

    public int getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public List<String> getParents() {
        return new ArrayList<>(parents);
    }
}
