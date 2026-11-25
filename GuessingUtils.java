import java.util.regex.*;

enum StatType {
  MEDIAN,
  MEAN,
  LONGEST,
  CURRENT
}

enum GameMode {
  MOVING,
  NO_MOVE,
  NMPZ,
  TURBO,
  UNKNOWN
}

public class GuessingUtils {

  static String standardizeDumbString(String dumbString)
  {
    String simpleString = dumbString.replace('1','*');
    simpleString = simpleString.replace('0','?');
    return simpleString;
  }

  static boolean isDumbString(String checkOn)
  {
    return checkOn.matches("[01*?]+");
  }

  static boolean isCompressedGuessString(String checkOn)
  {
    boolean testResult = true;

    //test for permitted characters, only *, ?, digits allowed
    boolean testPermittedChar = ! Pattern.compile("[^\\?\\*[0-9]]").matcher(checkOn).find();
    testResult = testResult && testPermittedChar;

    //test for last character, may not be a digit
    boolean testLastChar = checkOn.endsWith("*") || checkOn.endsWith("?");
    testResult = testResult && testLastChar;

    //test: numbers should not start with digit 0
    boolean testZeroStart = ! checkOn.startsWith("0")
                         && ! Pattern.compile("\\*0").matcher(checkOn).find()
                         && ! Pattern.compile("\\?0").matcher(checkOn).find();
    testResult = testResult && testZeroStart;

    //test: numbers may not be followed by ? (since they represent multiples of successes only)
    boolean testNumberFail = ! Pattern.compile("[0-9]\\?").matcher(checkOn).find();
    testResult = testResult && testNumberFail;

    //plausibility test: we believe maximum 3-digit numbers to be realistic
    boolean testMax3Digits = ! Pattern.compile("[1-9][0-9]{3}").matcher(checkOn).find();
    testResult = testResult && testMax3Digits;

    return testResult;
  }
}