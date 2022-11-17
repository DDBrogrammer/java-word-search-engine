package engine;

import java.util.List;

public class Result implements Comparable<Result>{
    private int matchCount;
    private int totalFrequency;
    private double averageFirstIndex;
    private Doc doc;
    private List<Match> matches;

    @Override
    public int compareTo(Result o) {
        if(this.matchCount!=o.getMatches().size()){
            return o.getMatches().size()-this.matchCount;
        }
        if(this.totalFrequency!=o.getTotalFrequency()){
            return o.getTotalFrequency()-this.totalFrequency;
        }
        if(this.averageFirstIndex!= o.getAverageFirstIndex()){
            return (int) (this.averageFirstIndex-o.getAverageFirstIndex());
        }
        return 0;
    }
    public Result(Doc doc, List<Match> matches){
        this.doc=doc;
        this.matches=matches;
        this.matchCount=matches.size();
        this.totalFrequency=0;
        int totalFirstIndex=0;
        for(Match match:matches){
            this.totalFrequency=match.getFreq()+this.totalFrequency;
            totalFirstIndex=match.getFirstIndex()+totalFirstIndex;
        }
        this.averageFirstIndex=totalFirstIndex/matchCount;
    }


    public Doc getDoc(){
               return this.doc;
    }


    public int getTotalFrequency(){
        return this.totalFrequency;
    }

    public List<Match> getMatches(){
        return this.matches;
    }


    public double getAverageFirstIndex(){
        return 0.0;
    }

    public String htmlHighlight(){
        StringBuilder sb = new StringBuilder();
        List<Word> titles=  this.doc.getTitle();
        List<Match> matchList=this.getMatches();
        sb.append("<h3>");
        for(Word word: titles){
            boolean isMatch=false;
            for(Match match: matchList){
                if(word.equals(match.getWord())){
                    isMatch=true;
                }
            }
            if (isMatch){
                sb.append(word.getPrefix());
                sb.append("<u>");
                sb.append(word.getText());
                sb.append("</u>");
                sb.append(word.getSuffix());
                sb.append(" ");
            }else{
                sb.append(word.getPrefix());
                sb.append(word.getText());
                sb.append(word.getSuffix());
                sb.append(" ");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("</h3>");
        sb.append("<p>");
        List<Word> bodies=this.doc.getBody();
        for(Word word: bodies){
            boolean isMatch=false;
            for(Match match: matchList){
                if(word.equals(match.getWord())){
                    isMatch=true;
                }
            }
            if (isMatch){
                sb.append(word.getPrefix());
                sb.append("<b>");
                sb.append(word.getText());
                sb.append("</b>");
                sb.append(word.getSuffix());
                sb.append(" ");
            }else{
                sb.append(word.getPrefix());
                sb.append(word.getText());
                sb.append(word.getSuffix());
                sb.append(" ");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("</p>");
        return sb.toString();
    }

}
