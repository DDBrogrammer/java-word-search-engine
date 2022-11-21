package engine;
import java.util.List;
import java.util.ArrayList;
public class Doc {
    public List<Word> getBody(){
        return this.body;
    }
    public List<Word> getTitle(){
        return this.title;
    }
    public Doc(String content) {
        this.title=new ArrayList<>();
        this.body=new ArrayList<>();
        String[] lines = content.split("\r\n|\n|\r");
        String[] docTiles= lines[0].split(" ");
        String[] docBodies= lines[1].split(" ");
        for(String item : docTiles)
        {
            Word tempItem = new Word(item);
            this.title.add(tempItem);
        }
        for(String item : docBodies)
        {
            Word tempItem = new Word(item);
            this.body.add(tempItem);
        }
    }

    public boolean equals(Object o){
        return false;
    }

    private List<Word> body;

    private List<Word> title;
}
