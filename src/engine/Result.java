package engine;

import java.util.List;

public class Result implements Comparable<Result> {
    private int matchCount;
    private int totalFrequency;
    private double averageFirstIndex;
    private Doc doc;
    private List<Match> matches;

    public Doc getDoc() {
        return this.doc;
    }

    public int getTotalFrequency() {
        return this.totalFrequency;
    }

    public List<Match> getMatches() {
        return this.matches;
    }

    public double getAverageFirstIndex() {
        return 0.0;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    public void setTotalFrequency(int totalFrequency) {
        this.totalFrequency = totalFrequency;
    }

    public void setAverageFirstIndex(double averageFirstIndex) {
        this.averageFirstIndex = averageFirstIndex;
    }

    public void setDoc(Doc doc) {
        this.doc = doc;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public Result(Doc doc, List<Match> matches) {
        this.doc = doc;
        this.matches = matches;
        this.matchCount = matches.size();
        this.totalFrequency = 0;
        int totalFirstIndex = 0;
        for (Match match : matches) {
            this.totalFrequency = match.getFreq() + this.totalFrequency;
            totalFirstIndex = match.getFirstIndex() + totalFirstIndex;
        }
        this.averageFirstIndex = (double) totalFirstIndex / matchCount;
    }

    public Result() {
    }

    @Override
    public String toString() {
        return "Result{" +
                "matchCount=" + matchCount +
                ", totalFrequency=" + totalFrequency +
                ", averageFirstIndex=" + averageFirstIndex +
                ", doc=" + doc +
                ", matches=" + matches +
                '}';
    }

    @Override
    public int compareTo(Result compareResult) {
        if (this.matchCount != compareResult.getMatches().size()) {
            return compareResult.getMatches().size() - this.matchCount;
        }
        if (this.totalFrequency != compareResult.getTotalFrequency()) {
            return compareResult.getTotalFrequency() - this.totalFrequency;
        }
        if (this.averageFirstIndex != compareResult.getAverageFirstIndex()) {
            return (int) (this.averageFirstIndex - compareResult.getAverageFirstIndex());
        }
        return 0;
    }

    public String htmlHighlight() {
        StringBuilder htmlStringBuilder = new StringBuilder();
        List<Word> docTitleWords = this.doc.getTitle();
        List<Match> docMatches = this.getMatches();
        htmlStringBuilder.append("<h3>");
        for (Word docTitleWord : docTitleWords) {
            boolean isMatch = false;
            for (Match match : docMatches) {
                if (docTitleWord.equals(match.getWord())) {
                    isMatch = true;
                }
            }
            if (isMatch) {
                htmlStringBuilder.append(docTitleWord.getPrefix());
                htmlStringBuilder.append("<u>");
                htmlStringBuilder.append(docTitleWord.getText());
                htmlStringBuilder.append("</u>");
                htmlStringBuilder.append(docTitleWord.getSuffix());
                htmlStringBuilder.append(" ");
            } else {
                htmlStringBuilder.append(docTitleWord.getPrefix());
                htmlStringBuilder.append(docTitleWord.getText());
                htmlStringBuilder.append(docTitleWord.getSuffix());
                htmlStringBuilder.append(" ");
            }
        }
        htmlStringBuilder.deleteCharAt(htmlStringBuilder.length() - 1);
        htmlStringBuilder.append("</h3>");
        htmlStringBuilder.append("<p>");
        List<Word> docBodyWords = this.doc.getBody();
        for (Word bodyWord : docBodyWords) {
            boolean isMatch = false;
            for (Match docMatch : docMatches) {
                if (bodyWord.equals(docMatch.getWord())) {
                    isMatch = true;
                }
            }
            if (isMatch) {
                htmlStringBuilder.append(bodyWord.getPrefix());
                htmlStringBuilder.append("<b>");
                htmlStringBuilder.append(bodyWord.getText());
                htmlStringBuilder.append("</b>");
                htmlStringBuilder.append(bodyWord.getSuffix());
                htmlStringBuilder.append(" ");
            } else {
                htmlStringBuilder.append(bodyWord.getPrefix());
                htmlStringBuilder.append(bodyWord.getText());
                htmlStringBuilder.append(bodyWord.getSuffix());
                htmlStringBuilder.append(" ");
            }
        }
        htmlStringBuilder.deleteCharAt(htmlStringBuilder.length() - 1);
        htmlStringBuilder.append("</p>");
        return htmlStringBuilder.toString();
    }
}
