import java.util.*;

public class Unscramble {
    private String scrambledWord;
    private String[] targets;
    private Map<String, Integer> totalOccurrenceMap;
    private Map<String, Integer> uniqueOccurrences;

    public Unscramble(String scrambledWord, String[] targets) {
        this.scrambledWord = scrambledWord;
        this.targets = targets;
        this.totalOccurrenceMap = generateTotalOccurrences(this.scrambledWord, this.targets);
        this.uniqueOccurrences = getExactOccurrences(this.totalOccurrenceMap);
    }

    public String chaseSolution() {
        String text = "****UNSCRAMBLE****" +
                      "\n-----------------------" +
                      "\nInitial string: " + this.scrambledWord +
                      "\nAs an int: " + mapToInt(this.uniqueOccurrences) +
                      "\n-----------------------";

        return text;
    }

    public String uniqueOccurrencesToString() {
        String text = "****Printed results map:****" +
                      "\n-----------------------";

        for (String target : this.targets) {
            text += "\n" + target + ": " + this.uniqueOccurrences.get(target);
        }

        text += "\n-----------------------";

        return text;
    }

    //Helper Methods

    private Map<String, Integer> getExactOccurrences(Map<String , Integer> totalOccurrences) {
        String fullString = getOccurrenceString(totalOccurrences);

        //If totalOccurrenceMap is already an anagram match to scrambleWord
        if (exactMatch(fullString, this.scrambledWord)) {
            return totalOccurrences;
        }

        return removeExtras(totalOccurrences, this.scrambledWord);
    }

    private Map<String , Integer> removeExtras(Map<String, Integer> mapWithExtras, String target) {
        Map<String, Integer> newMap = mapWithExtras;

        //get difference between target and occurrence string
        String extras = getExtraChars(target, getOccurrenceString(newMap));

        //Map extras to target words
        Map<String, Integer> extrasMap = generateTotalOccurrences(extras, this.targets);
        String extrasString = getOccurrenceString(extrasMap);

        //If extrasMap is not an anagram match to extras
        if (!exactMatch(extras, extrasString)) {
            //remove extras from extrasMap ...
            extrasMap = removeExtras(extrasMap, extras);
        }

        //Remove extrasMap from newMap
        for (String t : this.targets) {
            newMap.put(t, newMap.get(t) - extrasMap.get(t));
        }

        return newMap;
    }

    private Map<String, Integer> generateTotalOccurrences(String scrambledWord, String[] targets) {
        Map <String , Integer> results = new HashMap<>();

        for (String target : targets ) {
            results.put(target, wordTimes(scrambledWord, target));
        }

        return results;
    }

    private String getOccurrenceString(Map<String, Integer> occurrences) {
        String fullString = "";

        for (int i = 0; i < this.targets.length; i++) {
            int times = occurrences.get(this.targets[i]);

            for(int k = 0; k < times; k++) {
                fullString += this.targets[i];
            }
        }

        return fullString;
    }

    private int wordTimes(String word, String target) {
        Map<Character, Integer> matches = new HashMap<>();
        List<Pair> pairs = new ArrayList<>();

        //for each c in target
        for (Character c : target.toCharArray()) {
            //if c does not appear in word or appears too few times
            if (word.indexOf(c) == -1 || charTimes(c, target) > charTimes(c, word)) {
                return 0;
            } else {
                //Create Pair for c and times it appears in word
                int times = charTimes(c, word);

                Pair pair = new Pair(c, times);
                pairs.add(pair);
            }
        }

        //Adjust for duplicate letter in target
        for (Pair pair : pairs) {
            int timesInTarget = charTimes(pair.getLetter(), target);

            if (timesInTarget > 1) {
                pair.setTimes(pair.getTimes() / timesInTarget);
            }
        }

        //return smallest value in matches
        int pairFoundTimes = -1;

        for (Pair pair : pairs) {
            if (pairFoundTimes < 0) {
                pairFoundTimes = pair.getTimes();
            } else {
                pairFoundTimes = Math.min(pair.getTimes(), pairFoundTimes);
            }
        }

        return pairFoundTimes;
    }

    private int charTimes(char letter, String word) {
        int times = 0;

        for (char c : word.toCharArray()) {
            if (c == letter) {
                times++;
            }
        }

        return times;
    }

    private String getExtraChars(String shortString, String longString) {
        char[] wordArr = shortString.toCharArray();

        for (char letter : wordArr) {
            if (longString.indexOf(letter) != -1) {
                int index = longString.indexOf(letter);

                if (index == 0) {
                    longString = longString.substring(1);
                } else {
                    longString = longString.substring(0, index) + longString.substring(index + 1);
                }
            }
        }

        return longString;
    }

    private boolean exactMatch(String firstString, String secondString) {
        if (firstString.length() == secondString.length()) {
            char[] firstStringArr = firstString.toCharArray();
            char[] secondStringArr = secondString.toCharArray();

            Arrays.sort(firstStringArr);
            Arrays.sort(secondStringArr);

            return (String.valueOf(firstStringArr).equals(String.valueOf(secondStringArr)));
        }

        return false;
    }

    private int mapToInt(Map<String, Integer> map) {
        String number = "";

        for (int i = 0; i < this.targets.length; i++) {
            int times = map.get(this.targets[i]);

            for(int k = 0; k < times; k++) {
                number += i + 1;
            }
        }

        return Integer.parseInt(number);
    }

    public static void main(String[] args) {
        String word = "neotowtehrefurofivveseeneightnineofurneo";
        String[] targets = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        Unscramble unscramble = new Unscramble(word, targets);

        System.out.println(unscramble.chaseSolution());
        System.out.println();
        System.out.println(unscramble.uniqueOccurrencesToString());
    }
}

class Pair {
    private char letter;
    private int times;

    public Pair(char letter, int times) {
        this.letter = letter;
        this.times = times;
    }

    public char getLetter() {
        return letter;
    }

    public int getTimes() {
        return times;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}