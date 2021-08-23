import java.util.HashMap;
import java.util.Map;

public class Unscramble {
    private String scrambledWord;
    private String[] targets;

    public Unscramble(String scrambledWord, String[] targets) {
        this.scrambledWord = scrambledWord;
        this.targets = targets;
    }

    public Map<String, Integer> findTargetOccurrences() {
        Map <String , Integer> results = new HashMap<>();

        for (String target : this.targets ) {
            results.put(target, wordTimes(this.scrambledWord, target));
        }

        return results;
    }

    public int wordTimes(String word, String target) {
        Map<Character, Integer> matches = new HashMap<>();

        //for each c in target
        for (Character c : target.toCharArray()) {
            //if word does not contain target
            if (word.indexOf(c) == -1) {
                return 0;
            } else {
                //map how many times c appears
                int times = charTimes(c, word);

                //if c is a duplicate letter
                if (matches.containsKey(c)) {
                    matches.put(c, matches.get(c) + times);
                } else {
                    matches.put(c, times);
                }
            }
        }

        int foundTimes = -1;

        //loop through keys in map
        for (Map.Entry<Character, Integer> entry : matches.entrySet()) {
            //adjust for duplicates
            int timesInTarget = charTimes(entry.getKey(), target);
            int adjustedTimes = (timesInTarget > 1) ? entry.getKey() / timesInTarget :entry.getValue();
            //return smallest of values
            if (foundTimes < 0) {
                foundTimes = adjustedTimes;
            } else {
                foundTimes = Math.min(adjustedTimes, foundTimes);
            }
        }

        return foundTimes;
    }

    public int charTimes(char letter, String word) {
        int times = 0;

        for (char c : word.toCharArray()) {
            if (c == letter) {
                times++;
            }
        }

        return times;
    }

    public static void main(String[] args) {
        String word = "onefournine";
        String[] targets = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

        Unscramble unscramble = new Unscramble(word, targets);
        Map<String, Integer> results = unscramble.findTargetOccurrences();

        for (Map.Entry<String, Integer> entry : results.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}