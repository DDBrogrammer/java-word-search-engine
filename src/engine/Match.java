package engine;

import java.util.ArrayList;
import java.util.List;

public class Match implements Comparable<Match> {
    private int freq;
    private int firstIndex;
    private Doc doc;
    private Word word;

    public int getFreq() {
        return this.freq;
    }

    public int getFirstIndex() {
        return this.firstIndex;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public void setFirstIndex(int firstIndex) {
        this.firstIndex = firstIndex;
    }

    public Doc getDoc() {
        return doc;
    }

    public Word getWord() {
        return this.word;
    }

    public void setDoc(Doc doc) {
        this.doc = doc;
    }

    public void setWord(Word word) {
        this.word = word;
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

    @Override
    public int compareTo(Match match) {
        return this.getFirstIndex() - match.getFirstIndex();
    }

    public Match(Doc doc, Word word, int freq, int firstIndex) {
        this.doc = doc;
        this.word = word;
        this.freq = freq;
        this.firstIndex = firstIndex;
    }

    public Match(Doc doc, Word word) {
        List<Word> docWordList = new ArrayList<>();
        this.firstIndex = -1;
        this.freq = 0;
        int wordIndex = 0;
        docWordList.addAll(doc.getTitle());
        docWordList.addAll(doc.getBody());
        for (Word compareWord : docWordList) {
            wordIndex++;
            if (compareWord.equals(word)) {
                this.freq = this.freq + 1;
                if (this.firstIndex == -1) {
                    this.firstIndex = wordIndex;
                }
            }
        }
        this.word = word;
        this.doc = doc;
    }

    public Match() {
    }
}
