package com.openhouseai.codingtest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Paragraph {
    BufferedReader br;
    List<SearchedWord> list;
    public Paragraph() throws Exception {
        File file = new File("king-i.txt");
        br = new BufferedReader(new FileReader(file));
        list = new ArrayList<>();
    }
    public void find(String target) throws IOException {
        int lineCount = 0;
        target = target.toLowerCase();
        String line;
        while ((line = br.readLine()) != null) {
            line = line.toLowerCase();
            String regEx = "[?.!]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(line);
            String[] substrs = p.split(line);
            if (substrs.length > 0) {
                int count = 0;
                while (count < substrs.length) {
                    if (m.find()) {
                        substrs[count] += m.group();
                    }
                    count++;
                }
            }
            int index = 0;
            int end = 0;
            int start = 0;
            while (index > -1) {
                index = line.indexOf(target, index);
                if (index > 0) {
                    start = index;
                    end = index + target.length() - 1;
                    if (isValid(line, start, end)) {
                        SearchedWord word = new SearchedWord();
                        word.setLine(lineCount);
                        word.setStart(start);
                        word.setEnd(end + 1);
                        int lineIndex = getIndex(start, substrs);
                        word.setSentence(substrs[lineIndex].trim());
                        list.add(word);
                    }
                    index = end + 1;
                    if (index >= line.length()) {
                        break;
                    }
                }
            }
            lineCount++;
        }
        br.close();
    }
    private int getIndex(int start, String[] s) {
        for (int i = 0; i < s.length; i++) {
            if (start > s[i].length()) {
                start -= s[i].length();
            } else {
                return i;
            }
        }
        return s.length - 1;
    }

    private boolean isValid(String line, int start, int end) {
        if ((start == 0 || line.charAt(start - 1) - 'a' < 0 ||
                line.charAt(start - 1) - 'a' > 26) &&
                (end + 1 >= line.length() || line.charAt(end + 1) - 'a' < 0 ||
                        line.charAt(end + 1) - 'a' > 26)) {
            return true;
        }
        return false;
    }

}
