package engine;

import java.util.ArrayList;
import java.util.List;

public class Doc {
    private List<Word> title;
    private List<Word> body;

    public List<Word> getTitle() {
        return title;
    }

    public void setTitle(List<Word> title) {
        this.title = title;
    }

    public void setBody(List<Word> body) {
        this.body = body;
    }

    public List<Word> getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Doc{" +
                "title=" + title +
                ", body=" + body +
                '}';
    }

    public Doc() {
    }

    public Doc(String content) {
        String[] contentLines = content.split("\r\n|\n|\r");
        String[] contentTiles = contentLines[0].split(" ");
        String[] contentBodies = contentLines[1].split(" ");
        this.body = new ArrayList<>();
        this.title = new ArrayList<>();
        for (String titleWord : contentTiles) {
            Word tempTitleWord = new Word(titleWord);
            this.title.add(tempTitleWord);
        }
        for (String bodyWord : contentBodies) {
            Word tempBodyWord = new Word(bodyWord);
            this.body.add(tempBodyWord);
        }
    }
}
