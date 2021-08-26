import org.junit.Assert;

import static org.junit.Assert.*;

public class UnscrambleTest {

    @org.junit.Test
    public void chaseSolutionFor149() {
        String scramble = "onenruofine";
        String[] targets = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        String expected = "149";

        Unscramble unscramble = new Unscramble(scramble, targets);

        assertTrue(unscramble.chaseSolution().contains(expected));
    }

    @org.junit.Test
    public void chaseSolutionFor0123456789() {
        String scramble = "wootenoroufeehertxisvezfithgieveesnnnrie";
        String[] targets = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        String expected = "0123456789";

        Unscramble unscramble = new Unscramble(scramble, targets);

        assertTrue(unscramble.chaseSolution().contains(expected));
    }

    @org.junit.Test
    public void chaseSolutionFor0011122333334444567788899() {
        String scramble = "zerneninthgiethgiethgienevesnevesxisevifruofruofruofruofeerhteerhteerhteerhteerhtowtowtenoenoenoorezoine";
        String[] targets = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        String expected = "0011122333334444567788899";

        Unscramble unscramble = new Unscramble(scramble, targets);

        assertTrue(unscramble.chaseSolution().contains(expected));
    }
}