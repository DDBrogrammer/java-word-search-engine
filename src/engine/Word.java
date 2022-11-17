package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

public class Word {
    public static Set<String> stopWords;
    private String prefix;
    private String suffix;
    private String text;
    private boolean isKeyword;

    public boolean isKeyword() {
        return this.isKeyword;
    }

    public void setIsKeyword(boolean keyword) {
        this.isKeyword = keyword;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Word) {
            return ((Word) o).getText().equalsIgnoreCase(this.text);
        }
        return false;
    }

    @Override
    public String toString() {
        return this.text;
    }

    public Word() {
    }


    public Word(String rawText) {
        if (!this.validateKeyWord(rawText)) {
            this.setIsKeyword(false);
            this.setText(rawText);
            this.setPrefix("");
            this.setSuffix("");
        } else {
            String prefix = this.getPrefixFromRawText(rawText);
            this.setPrefix(prefix);
            String suffix = this.getSuffixFromRawText(rawText);
            this.setSuffix(suffix);
            String text = this.getTextFromRawText(rawText, prefix, suffix);
            this.setText(text);
            this.setIsKeyword(true);
        }
    }

    public static Word createWord(String rawText) {
        Word word = new Word(rawText);
        return word;
    }


    private boolean validateKeyWord(String rawText) {
        if (rawText.isEmpty()) {
            return false;
        }
        if (!rawText.matches("^[a-zA-Z!()«»\\-\\[\\]{};':\"\\\\,.<>\\/?]*$")) {
            return false;
        }
        if (stopWords.contains(rawText.toLowerCase(Locale.ROOT))) {
            return false;
        }
        return true;
    }


    private String getSuffixFromRawText(String rawText) {
        StringBuilder suffix = new StringBuilder("");
        String[] substrings = rawText.split("");
        boolean isSuffixEnd = false;
        boolean isSuffixEndWithS = false;
        for (int i = substrings.length - 1; i >= 0; i--) {
            if (substrings[i].matches("^[»!)\\-\\]};':\"\\\\,.>?]*$")) {
                if (!isSuffixEnd) {
                    suffix.append(substrings[i]);
                }
            } else {
                    isSuffixEnd = true;
            }
        }
        if(rawText.contains("'s")){
            suffix.append("s");
            suffix.append("'");
        }
        return suffix.reverse().toString();
    }

    private String getPrefixFromRawText(String rawText) {
        StringBuilder prefix = new StringBuilder("");
        boolean isPrefixEnd = false;
        String[] substrings = rawText.split("");
        for (String ch : substrings) {
            if (ch.matches("^[«(\\-\\[{\"<]*$")) {
                if (!isPrefixEnd) {
                    prefix.append(ch);
                }
            } else {
                isPrefixEnd = true;
            }
        }
        return prefix.toString();
    }

    private String getTextFromRawText(String rawText, String prefix, String suffix) {
        String result = rawText;
        if (prefix.length() > suffix.length()) {
            if (!prefix.isEmpty()) {
                result = rawText.replace(prefix, "");
            }
            if (!suffix.isEmpty()) {
                result = result.replace(suffix, "");
                /* result= replaceLast(result, suffix ,"");*/
            }
        } else {
            if (!suffix.isEmpty()) {
                result = rawText.replace(suffix, "");
                /* result= replaceLast(result, suffix ,"");*/
            }
            if (!prefix.isEmpty()) {
                result = result.replace(prefix, "");
            }
        }

        return result;
    }

    private String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)" + regex + "(?!.*?" + regex + ")", replacement);
    }

    public static boolean loadStopWords(String fileName) {
        try {
            Word.stopWords = new HashSet<>();
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Word.stopWords.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    public static void setStopWords(Set<String> stopWords) {
        Word.stopWords = stopWords;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setText(String text) {
        this.text = text;
    }
}
