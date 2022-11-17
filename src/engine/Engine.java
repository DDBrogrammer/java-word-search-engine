package engine;

import java.io.File;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Engine {
    private ArrayList<Doc> docs;

    public int loadDocs(String dirname) {
        this.docs = new ArrayList<Doc>();
        int fileNumber = 0;
        File folder = new File(dirname);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            fileNumber++;
            if (file.isFile() && file.getName().endsWith(".txt")) {
                String content = this.readFileToString(file, dirname);
                Doc tempDoc = new Doc(content);
                this.docs.add(tempDoc);
            }
        }
        return fileNumber;
    }

    private String readFileToString(File file, String dirname) {
        StringBuilder result = new StringBuilder();
        Path path = FileSystems.getDefault().getPath(dirname + "/" + file.getName());
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line : lines) {
            result.append(line).append("\n");
        }
        return result.toString();

    }

    public Doc[] getDocs() {
        return (Doc[]) this.docs.toArray();
    }

    public List<Result> search(Query query) {
        List<Result> resultList=new ArrayList<>();
        for (Doc doc : this.docs) {
            List<Match> tempListMatch = query.matchAgainst(doc);
            if(tempListMatch!=null&&tempListMatch.size()!=0){
                Result result=new Result(doc, tempListMatch);
                resultList.add(result);
            }
        }
        Collections.sort(resultList);
        return resultList;
    }

    public String htmlResult(List<Result> results) {
        StringBuilder sb = new StringBuilder();
        for(Result result:results){
            sb.append(result.htmlHighlight());
        }
        return sb.toString();
    }
}
