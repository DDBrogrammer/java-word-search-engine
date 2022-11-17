package engine;

import java.util.ArrayList;
import java.util.List;

public class Match implements Comparable<Match> {
    private int freq;
    private int firstIndex;
    private Doc doc;
    private Word word;

    @Override
    public int compareTo(Match match) {
        return this.getFirstIndex()-match.getFirstIndex();
    }

    public Match(Doc doc, Word word, int freq, int firstIndex) {
        this.doc = doc;
        this.word = word;
        this.freq=freq;
        this.firstIndex=firstIndex;

    }

    public Match(Doc doc, Word word) {
        List<Word> docWordList = new ArrayList<>();
        this.freq = 0;
        this.firstIndex = -1;
        docWordList.addAll(doc.getTitle());
        docWordList.addAll(doc.getBody());
        int index = 0;
        for (Word w : docWordList) {
            index++;
            if(w.equals(word)){
                this.freq= this.freq+1;
                if(this.firstIndex==-1){
                    this.firstIndex=index;
                }
            }
        }
        this.doc=doc;
        this.word=word;
    }

    public int getFreq() {
        return this.freq;
    }

    public int getFirstIndex() {
        return this.firstIndex;
    }

    @Override
    public String toString() {
        return "Match{" +
                "freq=" + freq +
                ", firstIndex=" + firstIndex +
                ", doc=" + doc +
                ", word=" + word.toString() +
                '}';
    }

    public Word getWord() {
        return this.word;
    }

}
