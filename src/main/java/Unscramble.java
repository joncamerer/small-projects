import java.util.*;

public class Unscramble {
    private String scrambledWord;
    private String[] targets;
    private Map<String, Integer> totalOccurrenceMap;
    private Map<String, Integer> uniqueOccurrenceMap;

    public Unscramble(String scrambledWord, String[] targets) {
        this.scrambledWord = scrambledWord;
        this.targets = targets;
        this.totalOccurrenceMap = generateTotalOccurrences(this.scrambledWord, this.targets);
        this.uniqueOccurrenceMap = generateUniqueOccurrences(this.totalOccurrenceMap);
    }

    public String chaseSolution() {

        return "\n****UNSCRAMBLE****" +
                "\n------------------" +
                "\nInitial string: " + this.scrambledWord +
                "\nAs a number: " + mapToNumber(this.uniqueOccurrenceMap);
    }

    public String uniqueOccurrencesToString() {
        StringBuilder text = new StringBuilder("""
                
                ****Unique occurrences****
                --------------------------""");

        for (String target : this.targets) {
            text.append("\n").append(target).append(": ").append(this.uniqueOccurrenceMap.get(target));
        }

        return text.toString();
    }

    //Helper Methods

    private Map<String, Integer> generateUniqueOccurrences(Map<String, Integer> totalOccurrences) {
        String fullString = getOccurrenceString(totalOccurrences);

        //If fullString of totalOccurrences is already an anagram match to scrambleWord
        if (exactMatch(fullString, this.scrambledWord)) {
            return totalOccurrences;
        }

        return removeExtras(totalOccurrences, this.scrambledWord);
    }

    private Map<String, Integer> removeExtras(Map<String, Integer> mapWithExtras, String target) {

        //Get string of difference between target and occurrence String of mapWithExtras
        String extras = getExtraChars(target, getOccurrenceString(mapWithExtras));

        //Map extras to target words
        Map<String, Integer> extrasMap = generateTotalOccurrences(extras, this.targets);
        String extrasString = getOccurrenceString(extrasMap);

        //If fullString of extrasMap is not an anagram match to extras
        if (!exactMatch(extras, extrasString)) {
            //remove extras from extrasMap ...
            removeExtras(extrasMap, extras);
        }

        //Remove extrasMap from newMap
        for (String t : this.targets) {
            mapWithExtras.put(t, mapWithExtras.get(t) - extrasMap.get(t));
        }

        return mapWithExtras;
    }

    private Map<String, Integer> generateTotalOccurrences(String scrambledWord, String[] targets) {
        Map <String , Integer> results = new HashMap<>();

        for (String target : targets ) {
            results.put(target, wordTimes(scrambledWord, target));
        }

        return results;
    }

    private int wordTimes(String word, String target) {
        List<Pair> occurrencePairs = new ArrayList<>();

        for (Character c : target.toCharArray()) {
            //if c does not appear in word or appears fewer times in word than target
            if (word.indexOf(c) == -1 ||  charTimes(c, word) < charTimes(c, target)) {
                return 0;
            } else {
                //Create Pair for c and times it appears in word
                int times = charTimes(c, word);
                Pair pair = new Pair(c, times);

                occurrencePairs.add(pair);
            }
        }

        return findLeastUse(adjustDuplicates(occurrencePairs, target));
    }

    private List<Pair> adjustDuplicates(List<Pair> occurrencePairs, String target) {
        for (Pair pair : occurrencePairs) {
            int timesInTarget = charTimes(pair.getLetter(), target);

            if (timesInTarget > 1) {
                pair.setTimes(pair.getTimes() / timesInTarget);
            }
        }
        return occurrencePairs;
    }

    private int findLeastUse(List<Pair> occurrencePairs) {
        int lowestTime = -1;

        for (Pair pair : occurrencePairs) {
            if (lowestTime < 0) {
                lowestTime = pair.getTimes();
            } else {
                lowestTime = Math.min(pair.getTimes(), lowestTime);
            }
        }

        return lowestTime;
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

    private String getOccurrenceString(Map<String, Integer> occurrences) {
        StringBuilder fullString = new StringBuilder();

        for (String target : this.targets) {
            int times = occurrences.get(target);

            fullString.append(String.valueOf(target).repeat(times));
        }

        return fullString.toString();
    }

    private String mapToNumber(Map<String, Integer> map) {
        StringBuilder number = new StringBuilder();

        for (int i = 0; i < this.targets.length; i++) {
            int times = map.get(this.targets[i]);

            number.append(String.valueOf(i).repeat(times));
        }

        return number.toString();
    }

    public static void main(String[] args) {
        String word = "onenruofine";
        String bigWord = "zerneninthgiethgiethgienevesnevesxisevifruofruofruofruofeerhteerhteerhteerhteerhtowtowtenoenoenoorezoine";
        String[] targets = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

        Unscramble unscrambleWord = new Unscramble(word, targets);
        Unscramble unscrambleBig = new Unscramble(bigWord, targets);

        System.out.println(unscrambleWord.chaseSolution());
        System.out.println(unscrambleBig.chaseSolution());
        System.out.println(unscrambleBig.uniqueOccurrencesToString());
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