import java.util.*;

public class Unscramble {
    private String scrambledWord;
    private String[] targets;
    private Map<String, Integer> totalOccurrenceMap;

    public Unscramble(String scrambledWord, String[] targets) {
        this.scrambledWord = scrambledWord;
        this.targets = targets;
        this.totalOccurrenceMap = generateTotalOccurrences(this.scrambledWord, this.targets);
    }

    public Map<String, Integer> getExactOccurrences() {
        String fullString = getOccurrenceString(this.totalOccurrenceMap);

        //If fullString is an anagram match to scrambleWord
        if (exactMatchScramble(fullString)) {
            return this.totalOccurrenceMap;
        }

        //Else
        //create new map to return
        Map<String, Integer> newMap = Map.copyOf(this.totalOccurrenceMap);

        //get difference between strings

        return newMap;
    }

    public String getExtraChars() {
        String fullString = getOccurrenceString(this.totalOccurrenceMap);

        char[] wordArr = this.scrambledWord.toCharArray();

        for (char letter : wordArr) {
            if (fullString.indexOf(letter) != -1) {
                int index = fullString.indexOf(letter);

                if (index == 0) {
                    fullString = fullString.substring(1);
                } else {
                    fullString = fullString.substring(0, index) + fullString.substring(index + 1);
                }
            }
        }

        return fullString;
    }

    public Map<String, Integer> generateTotalOccurrences(String scrambledWord, String[] targets) {
        Map <String , Integer> results = new HashMap<>();

        for (String target : targets ) {
            results.put(target, wordTimes(scrambledWord, target));
        }

        return results;
    }

    public Map<String, Integer> getTotalOccurrenceMap() {
        return totalOccurrenceMap;
    }

    public String getOccurrenceString(Map<String, Integer> occurrences) {
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

    private boolean exactMatchScramble(String fullString) {
        if (fullString.length() == this.scrambledWord.length()) {
            char[] scrambledWordArr = this.scrambledWord.toCharArray();
            char[] fullStringArr = fullString.toCharArray();

            Arrays.sort(scrambledWordArr);
            Arrays.sort(fullStringArr);

            return (String.valueOf(scrambledWordArr).equals(String.valueOf(fullStringArr)));
        }

        return false;
    }

    public static void main(String[] args) {
        String word = "noetowetreh";
        String word2 = "onefournine";
        String[] targets = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

        Unscramble unscramble = new Unscramble(word2, targets);
        Map<String, Integer> results = unscramble.getExactOccurrences();

        System.out.println();
        System.out.println(word2);
        System.out.println("Extras: " + unscramble.getExtraChars());
        System.out.println();

        //Numberify Map
        System.out.println("-----------------------");
        System.out.print("As an int: ");
        String numberString = "";

        for (int i = 0; i < targets.length; i++) {
            int times = results.get(targets[i]);

            for(int k = 0; k < times; k++) {
                numberString += i + 1;
            }
        }

        int number = Integer.parseInt(numberString);
        System.out.print(number);

        System.out.println();
        System.out.println("-----------------------");
        System.out.println();

        for (String target : targets) {
            System.out.println(target + ": " + results.get(target));
        }
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