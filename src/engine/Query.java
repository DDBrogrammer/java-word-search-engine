package engine;

import java.util.Collections;

import java.util.ArrayList;

import java.util.List;
public class Query {
    public List<Word> getKeywords(){

        return this.keywords;

    }
    public List<Match> matchAgainst(Doc doc){
        List<Match> matches = new ArrayList<>();

        for(Word item:keywords){

            Match tempMatch=new Match(doc,item);

            if(tempMatch.getFirstIndex()!=-1){

                matches.add(tempMatch);

            }
        }

        Collections.sort(matches);

        return matches;
    }

    private List<Word>
            keywords;

    public Query(String searchPhrase){

        String[] searchStrings= searchPhrase.split(" ");

        this.keywords=new ArrayList<>();

        for(String item :
                searchStrings)
        {
            Word tempWord = new Word(item);
            if(tempWord.isKeyword()){
                this.keywords.add(tempWord);
            }

        }


    }

}
