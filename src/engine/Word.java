package engine;


import java.util.Set;

import java.io.FileNotFoundException;

import java.util.Locale;

import java.util.Scanner;

import java.util.HashSet;


import java.io.File;
public class Word {
    private String suffix;

    public String getSuffix() {
        return this.suffix;
    }
    public boolean isKeyword() {
        return this.isKeyword;
    }

    public String getPrefix() {
        return this.prefix;
    }


    public String getText() {
        return this.text;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Word) {
            return ((Word) obj).getText().equalsIgnoreCase(this.text);
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
        if (!rawText.matches("^[a-zA-Z!()«»\\-\\[\\]}{;':\"\\\\,.><\\/?]*$")) {

            return false;
        }
        if (stopWords.contains(rawText.toLowerCase(Locale.ROOT))) {

            return false;
        }
        return true;
    }

    private String getTextFromRawText(String rawText, String prefix, String suffix) {
        String text = rawText;
        if (prefix.length() > suffix.length()) {

            if (!prefix.isEmpty()) {

                text = rawText.replace(prefix, "");
            }

            if (!suffix.isEmpty()) {

                text = text.replace(suffix, "");

            }

        } else {

            if (!suffix.isEmpty()) {

                text = rawText.replace(suffix, "");

            }

            if (!prefix.isEmpty()) {

                text = text.replace(prefix, "");

            }

        }
        return text;
    }

    private String getSuffixFromRawText(String rawText) {
        StringBuilder wordSuffix = new StringBuilder("");
        String[] rawTextChars = rawText.split("");
        boolean isSuffixEnd = false;
        for (int i = rawTextChars.length - 1; i >= 0; i--) {
            if (rawTextChars[i].matches("^[»!)\\-\\]};':\"\\\\,.>?]*$")) {
                if (!isSuffixEnd) {
                    wordSuffix.append(rawTextChars[i]);
                }
            } else {
                isSuffixEnd = true;
            }
        }
        if(rawText.contains("'s")){
            wordSuffix.append("s");
            wordSuffix.append("'");
        }
        return wordSuffix.reverse().toString();
    }

    private String getPrefixFromRawText(String rawText) {
        StringBuilder wordPrefix = new StringBuilder("");
        boolean isPrefixEnd = false;
        String[] rawTextChars = rawText.split("");
        for (String item : rawTextChars) {
            if (item.matches("^[<«(\\-\\[{\"]*$")) {
                if (!isPrefixEnd) {
                    wordPrefix.append(item);
                }
            } else {
                isPrefixEnd = true;
            }
        }
        return wordPrefix.toString();
    }

    public static boolean loadStopWords(String fileName) {
        try {
            Word.stopWords = new HashSet<>();
            File tempFile = new File(fileName);
            Scanner scanner = new Scanner(tempFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Word.stopWords.add(line);
            }
            scanner.close();
        } catch (FileNotFoundException fileNotFoundException) {
            return false;
        }
        return true;
    }

    public static void setStopWords(Set<String> stopWords) {
        Word.stopWords = stopWords;
    }

    private String prefix;


    public void setText(String text) {

        this.text = text;
    } public static Set<String> stopWords;



    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }


    private String text;
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


    private boolean isKeyword;

    public void setIsKeyword(boolean keyword) {
        this.isKeyword = keyword;
    }


}
