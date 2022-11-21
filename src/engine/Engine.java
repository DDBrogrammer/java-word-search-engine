package engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.charset.Charset;
public class Engine {
    public int loadDocs(String dirname) {
        this.docs = new ArrayList<Doc>();
        int fileIndex = 0;
        File inputFolder = new File(dirname);
        File[] files = inputFolder.listFiles();
        for (int i = 0; i < files.length; i++) {
            File fileItem = files[i];
            fileIndex++;
            if (fileItem.isFile() && fileItem.getName().endsWith(".txt")) {
                String fileContent = this.getStringsFromFile(fileItem, dirname);
                Doc tempDoc = new Doc(fileContent);
                this.docs.add(tempDoc);
            }
        }
        return fileIndex;
    }

    private String getStringsFromFile(File file, String dirname) {
        StringBuilder stringBuilder = new StringBuilder();
        Path filePath = FileSystems.getDefault().getPath(dirname + "/" + file.getName());
        List<String> tempLines = null;
        try {
            tempLines = Files.readAllLines(filePath, Charset.defaultCharset());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        for (String item : tempLines) {
            stringBuilder.append(item).append("\n");
        }
        return stringBuilder.toString();

    }
    public List<Result> search(Query query) {
        List<Result> results=new ArrayList<>();
        for (Doc item : this.docs) {
            List<Match> tempMatchs = query.matchAgainst(item);
            if(tempMatchs!=null && tempMatchs.size()!=0){
                Result tempResult=new Result(item, tempMatchs);
                results.add(tempResult);
            }
        }
        Collections.sort(results);
        return results;
    }

    public Doc[] getDocs() {
        return (Doc[]) this.docs.toArray();
    }

    public String htmlResult(List<Result> results) {
        StringBuilder stringBuilder = new StringBuilder();
        for(Result resultItem : results){
            stringBuilder.append(resultItem.htmlHighlight());
        }
        return stringBuilder.toString();
    }

    private ArrayList<Doc> docs;
}
