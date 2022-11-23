package engine;

import java.io.File;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Engine {
    private List<Doc> docs;

    public Doc[] getDocs() {
        return (Doc[]) this.docs.toArray();
    }

    public void setDocs(List<Doc> docs) {
        this.docs = docs;
    }

    @Override
    public String toString() {
        return "Engine{" +
                "docs=" + docs +
                '}';
    }

    public Engine(List<Doc> docs) {
        this.docs = docs;
    }

    public Engine() {
    }

    public int loadDocs(String dirname) {
        this.docs = new ArrayList<>();
        int fileIndexNumber = 0;
        File dirFolder = new File(dirname);
        File[] files = dirFolder.listFiles();
        for (int i = 0; i < files.length; i++) {
            File tempFile = files[i];
            fileIndexNumber++;
            if (tempFile.isFile() && tempFile.getName().endsWith(".txt")) {
                String fileContent = this.readFile(tempFile, dirname);
                Doc tempDoc = new Doc(fileContent);
                this.docs.add(tempDoc);
            }
        }
        return fileIndexNumber;
    }

    public List<Result> search(Query query) {
        List<Result> results = new ArrayList<>();
        for (Doc doc : this.docs) {
            List<Match> tempMatches = query.matchAgainst(doc);
            if (tempMatches != null && tempMatches.size() != 0) {
                Result tempResult = new Result(doc, tempMatches);
                results.add(tempResult);
            }
        }
        Collections.sort(results);
        return results;
    }

    private String readFile(File file, String dirname) {
        List<String> fileLines = new ArrayList<>();
        Charset charset = StandardCharsets.UTF_8;
        StringBuilder fileStringBuilder = new StringBuilder();
        Path filePath = FileSystems.getDefault().getPath(dirname + "/" + file.getName());
        try {
            fileLines = Files.readAllLines(filePath, charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String fileLine : fileLines) {
            fileStringBuilder.append(fileLine).append("\n");
        }
        return fileStringBuilder.toString();
    }

    public String htmlResult(List<Result> results) {
        StringBuilder resultStringBuilder = new StringBuilder();
        for (Result result : results) {
            resultStringBuilder.append(result.htmlHighlight());
        }
        return resultStringBuilder.toString();
    }
}
