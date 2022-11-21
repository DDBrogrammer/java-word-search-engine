package engine;

import java.util.List;

public class Result implements Comparable<Result>{
    public String htmlHighlight(){
        StringBuilder stringBuilder = new StringBuilder();
        List<Word> docTitles=  this.doc.getTitle();
        List<Match> matches= this.getMatches();
        stringBuilder.append("<h3>");
        for(Word item: docTitles){
            boolean isNeededWord=false;
            for(Match match: matches){
                if(item.equals(match.getWord())){
                    isNeededWord=true;
                }
            }
            if (isNeededWord){
                stringBuilder.append(item.getPrefix());
                stringBuilder.append("<u>");
                stringBuilder.append(item.getText());
                stringBuilder.append("</u>");
                stringBuilder.append(item.getSuffix());
                stringBuilder.append(" ");
            }else{
                stringBuilder.append(item.getPrefix());
                stringBuilder.append(item.getText());
                stringBuilder.append(item.getSuffix());
                stringBuilder.append(" ");
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("</h3>");
        stringBuilder.append("<p>");
        List<Word> docBodies=this.doc.getBody();
        for(Word item: docBodies){
            boolean isNeededWord=false;
            for(Match matchItem: matches){
                if(item.equals(matchItem.getWord())){
                    isNeededWord=true;
                }
            }
            if (isNeededWord){
                stringBuilder.append(item.getPrefix());
                stringBuilder.append("<b>");


                stringBuilder.append(item.getText());

                stringBuilder.append("</b>");

                stringBuilder.append(item.getSuffix());

                stringBuilder.append(" ");
            }else{
                stringBuilder.append(item.getPrefix());

                stringBuilder.append(item.getText());

                stringBuilder.append(item.getSuffix());
                stringBuilder.append(" ");
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        stringBuilder.append("</p>");

        return stringBuilder.toString();
    }

    @Override
    public int compareTo(Result result) {
        if( this.matchCount !=  result.getMatches( ).size( ) ) {
            return result.getMatches().size( ) - this.matchCount ;
        }
        if( this.totalFrequency  !=  result.getTotalFrequency( ) ) {
            return result.getTotalFrequency() - this.totalFrequency ;
        }
        if( this.averageFirstIndex !=  result.getAverageFirstIndex( ) ) {
            return ( int ) ( this.averageFirstIndex - result.getAverageFirstIndex( ) );
        }
        return 0 ;
    }

    private int totalFrequency ;
    public Result(Doc doc, List<Match> matches){
        this.matchCount=matches.size();
        this.totalFrequency=0;
        this.doc=doc;
        this.matches=matches;
        int totalFirstIndex=0;
        for(Match item:matches){
            this.totalFrequency=this.totalFrequency+ item.getFreq();
            totalFirstIndex=item.getFirstIndex()+totalFirstIndex;
        }
        this.averageFirstIndex=totalFirstIndex/matchCount;
    }


    public Doc getDoc(){
        return this.doc;
    }

    public double getAverageFirstIndex(){
        return 0.0;
    }



    private double averageFirstIndex;
    private int matchCount;

    public int getTotalFrequency ( ) {
        return this.totalFrequency;
    }
    private Doc doc;
    public List<Match> getMatches(){
        return this.matches;
    }
    private List<Match> matches;
}
