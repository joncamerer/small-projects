import java.util.HashMap;
import java.util.Map;

public class Unscramble {
    private String scrambledWord;
    private String[] targets;

    public Unscramble(String scrambledWord, String[] targets) {
        this.scrambledWord = scrambledWord;
        this.targets = targets;
    }

    public Map<String, Integer> getTargetOccurrences() {
        Map <String , Integer> results = new HashMap<>();

        for (String target : this.targets ) {
            results.put(target, wordTimes(this.scrambledWord, target));
        }

        return results;
    }

    private int wordTimes(String word, String target) {
        Map<Character, Integer> matches = new HashMap<>();

        //for each c in target
        for (Character c : target.toCharArray()) {
            //if c does not appear in word
            if (word.indexOf(c) == -1) {
                return 0;
            } else {
                //map how many times c appears in word
                int times = charTimes(c, word);

                //handle duplicate c
                if (matches.containsKey(c)) {
                    matches.put(c, matches.get(c) + times);
                } else {
                    matches.put(c, times);
                }
            }
        }

        //return smallest value in matches
        int foundTimes = -1;

        for (Map.Entry<Character, Integer> entry : matches.entrySet()) {
            int timesInTarget = charTimes(entry.getKey(), target);
            int adjustedTimes = (timesInTarget > 1) ? entry.getKey() / timesInTarget :entry.getValue();

            if (foundTimes < 0) {
                foundTimes = adjustedTimes;
            } else {
                foundTimes = Math.min(adjustedTimes, foundTimes);
            }
        }

        return foundTimes;
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

    public static void main(String[] args) {
        String word = "onefournine";
        String word2 = "onethreefoursevennine";
        String[] targets = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

        Unscramble unscramble = new Unscramble(word, targets);
        Map<String, Integer> results = unscramble.getTargetOccurrences();

        for (int i = 0; i < targets.length; i++) {
            int times = results.get(targets[i]);

            for(int k = 0; k < times; k++) {
                System.out.print(i + 1);
            }
        }

        System.out.println();
        System.out.println();
        System.out.println("-----------------------");
        System.out.println();

        for (String target : targets) {
            System.out.println(target + ": " + results.get(target));
        }
    }
}