package engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Query {

    private List<Word> keywords;

    public List<Word> getKeywords() {
        return this.keywords;
    }

    public void setKeywords(List<Word> keywords) {
        this.keywords = keywords;
    }

    public Query(String searchPhrase) {
        String[] searchPhrases = searchPhrase.split(" ");
        this.keywords = new ArrayList<>();
        for (String word : searchPhrases) {
            Word temp = new Word(word);
            if (temp.isKeyword()) {
                this.keywords.add(temp);
            }
        }
    }

    public Query() {
    }

    @Override
    public String toString() {
        return "Query{" +
                "keywords=" + keywords +
                '}';
    }

    public List<Match> matchAgainst(Doc doc) {
        List<Match> matchResults = new ArrayList<>();
        for (Word keyword : keywords) {
            Match match = new Match(doc, keyword);
            if (match.getFirstIndex() != -1) {
                matchResults.add(match);
            }
        }
        Collections.sort(matchResults);
        return matchResults;
    }

}
