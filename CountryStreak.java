import java.time.*;
import java.time.format.*;
import java.util.ArrayList;
//import org.apache.commons.statistics.variance;
import java.util.Scanner;
import java.util.regex;
import java.util.regex.Pattern;

enum StatType {
  MEDIAN,
  MEAN,
  LONGEST,
  CURRENT
}

public class CountryStreak {
  static String guessesAsDumbString;
  static ArrayList<Double> timeLineSuccessRate = new ArrayList<Double>();
  static ArrayList<Integer> seriesOfStreaks = new ArrayList<Integer>();

  static double getGuessingStats(StatType type)
  {
    inferSeriesOfStreaks();

    switch(type)
    {
      case StatType.LONGEST: 
        int best = 0;
        for( int i=0; i<seriesOfStreaks.size(); i++ )
          if ( seriesOfStreaks.get(i).intValue() > best )
            best = seriesOfStreaks.get(i).intValue();
        return best;
      case StatType.CURRENT:
          if (guessesAsDumbString.endsWith("1") || guessesAsDumbString.endsWith("*"))
            return seriesOfStreaks.getLast().intValue();
          else
            return 0;
      default:
          return -1;
    }
  }

  static void standardizeDumbString()
  {
    guessesAsDumbString = guessesAsDumbString.replace('1','*');
    guessesAsDumbString = guessesAsDumbString.replace('0','?');
  }

  static boolean isCompressedGuessString(String checkOn)
  {
    boolean testResult = true;
    boolean matchFound;
    Matcher matcher;

    //test for permitted characters
    Pattern pattern1 = Pattern.compile("[^\\?\\*[0-9]]");
    matcher = pattern1.matcher(checkOn);
    matchFound = matcher.find();
    if(matchFound)
    {
      IO.println("complaint!");
      return false;
    }
    else
      IO.println("no ineligible characters found");

    testResult = testResult && !(matchFound);

    //test for...

    return testResult;
  }

  static double successRateOfDumbString(String s)
  {
    int hits = 0;
    for(int i=0;i<s.length();i++)
      if ((s.charAt(i) == '1') || (s.charAt(i) == '*'))
        hits ++;
    return (double)hits / s.length();
  }

  static double successRateOfPartialString(String s, int start, int end)
  {
    return successRateOfDumbString(s.substring(start,end));
  }
  
  static void inferPartialSuccessRates(int partialStreakLength)
  {
    timeLineSuccessRate.clear();
    if ( partialStreakLength > 0  &&  partialStreakLength <= guessesAsDumbString.length() )
      for(int i=0; i <= guessesAsDumbString.length() - partialStreakLength; i++ )
        timeLineSuccessRate.add( successRateOfPartialString( guessesAsDumbString, i, i+partialStreakLength ));
  }

  static void inferSeriesOfStreaks()
  {
    seriesOfStreaks.clear();
    int currentStreak = 0;
    for(int i=0; i < guessesAsDumbString.length(); i++)
    {
      if (guessesAsDumbString.charAt(i) == '1' || guessesAsDumbString.charAt(i) == '*')
        currentStreak++;
      else
      {
        if (currentStreak > 0)
        {
          seriesOfStreaks.add(currentStreak);
          currentStreak = 0;
        }
      }
    }
    if (currentStreak > 0)
      seriesOfStreaks.add(currentStreak);
  }

  static void showStats()
  {
    System.out.println("Your country streak is: " + guessesAsDumbString);
    System.out.println("Your success rate is: " + successRateOfDumbString(guessesAsDumbString));
    inferPartialSuccessRates(5);
    System.out.println("in chunks of 5 guesses, your success rate developed as follows:");
    System.out.println(timeLineSuccessRate);
    System.out.println("you had the following streaks:");
    inferSeriesOfStreaks();
    System.out.println(seriesOfStreaks);
    System.out.println("your longest streak was:");
    System.out.println((int)getGuessingStats(StatType.LONGEST));
    System.out.println("your streak average was:");
    System.out.println(getGuessingStats(StatType.MEAN));
    System.out.println("your median streak was:");
    System.out.println((int)getGuessingStats(StatType.MEDIAN));
    System.out.println("your current streak is at:");
    System.out.println((int)getGuessingStats(StatType.CURRENT));
  }

  static void addGuess()
  {
    String guessInput = new java.util.Scanner(System.in).next();
    System.out.println("input was read as " + guessInput);
    SingleGuess g = new SingleGuess();
    g.guessDate = LocalDate.now();
    g.success = guessInput.contentEquals("*") || guessInput.contentEquals("1") || guessInput.contentEquals("y")
                ? true : false;
    guessesAsDumbString += g.success ? '*' : '?';

    DateTimeFormatter formatAtGuessCreation = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    System.out.println("Your guess was registered as '"
                        + g.success + "' on "
                        + g.guessDate.format(formatAtGuessCreation));
  }

  public static void main(String[] args) {
    int state = 0;

    //Input Data
    guessesAsDumbString = "10?10***1?";
    String guessesAsCompressedString = "*?3*??2*";

    //stuff for debugging regex search
    String drx = "[^\\?\\*[0-9]]";
    IO.println("passed pattern string:");
    IO.println(drx);
    


    //sanitize Input Data
    standardizeDumbString();

    //Main Menu
    do
    {
      System.out.println("1 - show stats, 2 - add guess, else - end");
      state = new java.util.Scanner(System.in).nextInt();
      switch (state)
      {
        case 1:
          System.out.println(" --- showing stats ---");
          showStats();
          break; 
        case 2:
          System.out.println(" --- add a guess ---\n successful? (1,*,y if yes)");
          addGuess();
          break;
        default: state = 0;
      }
    }
    while (state > 0);
    System.out.println(" --- bye ---");   
  }
}