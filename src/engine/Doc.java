package engine;

import java.util.ArrayList;
import java.util.List;

public class Doc {
    private List<Word> title;
    private List<Word> body;

    public Doc(String content) {
        String[] lines = content.split("\r\n|\n|\r");
        String[] tiles= lines[0].split(" ");
        String[] bodies= lines[1].split(" ");
        this.title=new ArrayList<>();
        this.body=new ArrayList<>();
        for(String word : tiles)
        {
            Word temp = new Word(word);
            this.title.add(temp);
        }
        for(String word : bodies)
        {
            Word temp = new Word(word);
            this.body.add(temp);
        }
    }

    public List<Word> getTitle(){
        return title;
    }

    public List<Word> getBody(){
        return body;
    }

    public boolean equals(Object o){
        return false;
    }
}
