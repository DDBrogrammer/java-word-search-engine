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

    public static Set<String> getStopWords() {
        return stopWords;
    }

    public void setKeyword(boolean keyword) {
        isKeyword = keyword;
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

    @Override
    public boolean equals(Object compareObject) {
        if (compareObject instanceof Word) {
            return ((Word) compareObject).getText().equalsIgnoreCase(this.text);
        }
        return false;
    }

    @Override
    public String toString() {
        return this.text;
    }

    public Word(String rawText) {
        if (!this.validateKeyWordFromRawText(rawText)) {
            this.setIsKeyword(false);
            this.setText(rawText);
            this.setPrefix("");
            this.setSuffix("");
        } else {
            String prefix = this.getRawTextPrefix(rawText);
            this.setPrefix(prefix);
            String suffix = this.getRawTextSuffix(rawText);
            this.setSuffix(suffix);
            String text = this.getTextFromRawText(rawText, prefix, suffix);
            this.setText(text);
            this.setIsKeyword(true);
        }
    }

    private boolean validateKeyWordFromRawText(String rawText) {
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

    private String getRawTextPrefix(String rawText) {
        StringBuilder prefixStringBuilder = new StringBuilder("");
        boolean isPrefixEnd = false;
        String[] rawTextChars = rawText.split("");
        for (String rawTextChar : rawTextChars) {
            if (rawTextChar.matches("^[«(\\-\\[{\"<]*$")) {
                if (!isPrefixEnd) {
                    prefixStringBuilder.append(rawTextChar);
                }
            } else {
                isPrefixEnd = true;
            }
        }
        return prefixStringBuilder.toString();
    }

    private String getRawTextSuffix(String rawText) {
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
        if (rawText.contains("'s")) {
            suffix.append("s");
            suffix.append("'");
        }
        return suffix.reverse().toString();
    }

    public Word() {
    }

    public static Word createWord(String rawText) {
        return new Word(rawText);
    }

    private String getTextFromRawText(String rawText, String rawTextPrefix, String rawTextSuffix) {
        String resultText = rawText;
        if (rawTextPrefix.length() > rawTextSuffix.length()) {
            resultText = rawText.replace(rawTextPrefix, "");
            if (!rawTextSuffix.isEmpty()) {
                resultText = resultText.replace(rawTextSuffix, "");
            }
        } else {
            if (!rawTextSuffix.isEmpty()) {
                resultText = rawText.replace(rawTextSuffix, "");
            }
            if (!rawTextPrefix.isEmpty()) {
                resultText = resultText.replace(rawTextPrefix, "");
            }
        }
        return resultText;
    }

    public static boolean loadStopWords(String fileName) {
        try {
            Word.stopWords = new HashSet<>();
            File file = new File(fileName);
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String fileLine = fileScanner.nextLine();
                Word.stopWords.add(fileLine);
            }
            fileScanner.close();
        } catch (FileNotFoundException fileNotFoundException) {
            return false;
        }
        return true;
    }
}
