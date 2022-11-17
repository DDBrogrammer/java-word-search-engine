package engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Query {

    private List<Word> keywords;

    public Query(String searchPhrase){
        String[] searchPhrases= searchPhrase.split(" ");
        this.keywords=new ArrayList<>();
        for(String word : searchPhrases)
        {
            Word temp = new Word(word);
            if(temp.isKeyword()){
                this.keywords.add(temp);
            }
        }
    }

    public List<Word> getKeywords(){
        return this.keywords;
    }

    public List<Match> matchAgainst(Doc doc){
        List<Match> results = new ArrayList<>();
        for(Word word:keywords){
            Match match=new Match(doc,word);
            if(match.getFirstIndex()!=-1){
                results.add(match);
            }
        }
        Collections.sort(results);
        return results;
    }


}
