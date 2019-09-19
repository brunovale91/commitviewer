package com.codacy.CommitViewer.git;

import com.codacy.CommitViewer.model.Commit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GithubCommitsDao implements CommitsDao {

    private static final String GITHUB_API_URL = "https://api.github.com/repos";

    @Override
    public List<Commit> getCommits(String owner, String repo, int page) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String url =GITHUB_API_URL + "/" + owner + "/" + repo + "/commits?page=" + page;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Failed to get commits. Status code: " + response.getStatusCode());
        }
        List<Commit> commits = new ArrayList<>();
        if (response.getBody() != null) {
            parseResponse(response, commits);
        }
        return commits;
    }

    private void parseResponse(ResponseEntity<String> response, List<Commit> commits) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONArray commitsArray = (JSONArray) parser.parse(response.getBody());
        for (Object obj : commitsArray) {
            parseCommit(commits, (JSONObject) obj);
        }
    }

    private void parseCommit(List<Commit> commits, JSONObject commitObj) {
        JSONObject commitDesc = (JSONObject) commitObj.get("commit");
        JSONObject author = (JSONObject) commitDesc.get("author");
        String id = (String) commitObj.get("sha");
        String message = (String) commitDesc.get("message");
        String authorEmail = (String) author.get("email");
        ZonedDateTime dateTime = (ZonedDateTime) DateTimeFormatter.ISO_DATE_TIME.parse((CharSequence) author.get("date"));
        int time = (int) dateTime.toEpochSecond();
        JSONArray parentsArray = (JSONArray) commitObj.get("parents");
        List<String> parents = new ArrayList<>();
        for (Object parentObj : parentsArray) {
            JSONObject parent = (JSONObject) parentObj;
            parents.add((String) parent.get("sha"));
        }
        commits.add(new Commit(id, time, authorEmail, message, parents));
    }
}
