import java.util.regex.*;
import java.util.ArrayList;

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

  static ArrayList<Integer> getStreaksFromSimpleString(String ss)
  {
    ArrayList<Integer> streaks = new ArrayList<>();
    int currentStreak = 0;
    for(int i=0; i < ss.length(); i++)
    {
      if (ss.charAt(i) == '1' || ss.charAt(i) == '*')
        currentStreak++;
      else if (currentStreak > 0)
        {
          streaks.add(currentStreak);
          currentStreak = 0;
        }
    }
    if (currentStreak > 0)
      streaks.add(currentStreak);
    return streaks;
  }

  static double successRateOfSimpleString(String s)
  {
    int hits = 0;
    for(int i=0;i<s.length();i++)
      if ((s.charAt(i) == '1') || (s.charAt(i) == '*'))
        hits ++;
    return (double)hits / s.length();
  }

  static ArrayList<Double> getPartialSuccessRatesFromSimpleString(int chunk, String ss)
  {
    //todo: prüfen auf korrektes format von ss, absichern mit exception
    ArrayList<Double> psr = new ArrayList<Double>();
    if ( chunk > 0  &&  chunk <= ss.length() )
      for(int i=0; i <= ss.length() - chunk; i++ )
        psr.add( successRateOfSimpleString( ss.substring(i, i+chunk) ) );
    return psr;
  }

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

  static double getGuessingStatsFromSimpleString(StatType type, String ss)
  {
    var streaks = GuessingUtils.getStreaksFromSimpleString(ss);
    switch(type)
    {
      case StatType.LONGEST:
        return streaks.stream().mapToInt(Integer::intValue).max().orElse(0);
      case StatType.CURRENT:
          if (ss.endsWith("1") || ss.endsWith("*"))
            return streaks.getLast().intValue();
          else
            return 0;
      case StatType.MEAN:
        return streaks.stream().mapToDouble(Integer::intValue).average().orElse(0.);
      case StatType.MEDIAN:
        ArrayList<Integer> seriesForMedian = new ArrayList<>(streaks);
        seriesForMedian.sort(null);
        return 0.5* (
                      seriesForMedian.get( (int)( (seriesForMedian.size()-0.99)/2) )
                    + seriesForMedian.get( (int)( (seriesForMedian.size()+0.01)/2) )
                    );
      default:
          return -1;
    }
  }
}