package engine;
import java.util.List;
import java.util.ArrayList;

public class Match implements Comparable<Match> {
    public Match(Doc doc, Word word) {
        List<Word> docWords = new ArrayList<>();
        this.firstIndex = -1;
        this.freq = 0;
        docWords.addAll(doc.getTitle());
        docWords.addAll(doc.getBody());
        int itemIndex = 0;
        for (Word item : docWords) {
            itemIndex++;
            if(item.equals(word)){
                this.freq= this.freq+1;
                if(this.firstIndex==-1){
                    this.firstIndex=itemIndex;
                }
            }
        }
        this.word=word;
        
        this.doc=doc;
    }
    private Doc doc;
    @Override
    public String toString() {
        return "Match{" +
                "freq=" + freq +
                ", firstIndex=" + firstIndex +
                ", doc=" + doc +
                ", word=" + word.toString() +
                '}';
    }
    private int freq;
    public int getFirstIndex() {
        return this.firstIndex;
    }
    public Match(Doc doc, Word word, int freq, int firstIndex) {
        this.word = word;
        this.doc = doc;
        this.firstIndex=firstIndex;
        this.freq=freq;
    }
    public int getFreq() {
        return this.freq;
    }

    @Override
    public int compareTo(Match match) {
        return this.getFirstIndex()-match.getFirstIndex();
    }
    public Word getWord() {
        return this.word;
    }

    private int firstIndex;
    private Word word;
}
