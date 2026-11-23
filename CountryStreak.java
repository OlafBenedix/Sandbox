import java.time.*;
import java.time.format.*;
import java.util.ArrayList;
import java.util.stream.DoubleStream;
import java.util.OptionalDouble;
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
        return seriesOfStreaks.stream().mapToInt(Integer::intValue).max().orElse(0);
      case StatType.CURRENT:
          if (guessesAsDumbString.endsWith("1") || guessesAsDumbString.endsWith("*"))
            return seriesOfStreaks.getLast().intValue();
          else
            return 0;
      case StatType.MEAN:
        return seriesOfStreaks.stream().mapToDouble(Integer::intValue).average().orElse(0.);
      case StatType.MEDIAN:
        var seriesForMedian = new ArrayList<>(seriesOfStreaks);
        seriesForMedian.sort(null);
        return 0.5* (
                      seriesForMedian.get( (int)( (seriesForMedian.size()-0.99)/2) )
                    + seriesForMedian.get( (int)( (seriesForMedian.size()+0.01)/2) )
                    );
      default:
          return -1;
    }
  }

  static void standardizeDumbString()
  {
    guessesAsDumbString = guessesAsDumbString.replace('1','*');
    guessesAsDumbString = guessesAsDumbString.replace('0','?');
  }

  static boolean isDumbString(String checkOn)
  {
    boolean testResult = ! Pattern.compile("[^01\\?\\*]").matcher(checkOn).find();
    //IO.println("Test isDumbString ist " + testResult);
    return testResult;
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
      else if (currentStreak > 0)
        {
          seriesOfStreaks.add(currentStreak);
          currentStreak = 0;
        }
    }
    if (currentStreak > 0)
      seriesOfStreaks.add(currentStreak);
  }

  static void browseGuesses()
  {
    //placeholder for later
    //once Class GuessHistory is implemented, more interesting choices can be offered
    //for now just print the guessStrings
    IO.println("Your country streak is: " + guessesAsDumbString);
    //IO.println("In compact form: " + guessesAsCompressedString);
  }

  static void addGuess()
  {
    IO.println(" --- add a guess ---\n successful? (1,*,y if yes)");      
    String guessInput = IO.readln();
    SingleGuess g = new SingleGuess(
      guessInput.contentEquals("*") || guessInput.contentEquals("1") || guessInput.contentEquals("y"),
      LocalDate.now()
        );
    guessesAsDumbString += g.getSuccess() ? '*' : '?';

    DateTimeFormatter formatAtGuessCreation = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    IO.println("Your guess was registered as '"
                        + g.getSuccess() + "' on "
                        + g.getGuessDate().format(formatAtGuessCreation)
                        + " with Guess ID# " + g.getGuessID()
                        + " in game mode " + g.getGameMode());
  }

  static void showStatsSuccessRates()
  {
    IO.print("Your overall success rate is: %.2f%% over %d guesses.%n%n".formatted(100*successRateOfDumbString(guessesAsDumbString),guessesAsDumbString.length()));
    int choice = 0;

    do {
      IO.println("show partial success rate for series of how many guesses? (0 to exit)");
      choice = Integer.parseInt(IO.readln().trim());//orElse(0)
      if (choice > 0) {
        inferPartialSuccessRates(choice);
        IO.println("in chunks of " + choice + " guesses, your success rate developed as follows:");
        IO.println(timeLineSuccessRate.stream().map("%.2f"::formatted).toList());
      }
    }
    while (choice != 0);
  }

  static void showStatsStreaks()
  {
    inferSeriesOfStreaks();
    IO.println("you had the following streaks:\n"+seriesOfStreaks);
    IO.println("your longest streak was: %d%n".formatted((int)getGuessingStats(StatType.LONGEST)));
    IO.println("your streak average was: %.2f%n".formatted(getGuessingStats(StatType.MEAN)));
    IO.println("your median streak was: " + getGuessingStats(StatType.MEDIAN));
    IO.println("your current streak is at: " + (int)getGuessingStats(StatType.CURRENT));
  }

  public static void main(String[] args) {
    int state = 0;

    GuessHistory testGH = new GuessHistory();
    testGH.addGuess(new SingleGuess(true, LocalDate.now()));
    testGH.addGuess(new SingleGuess(false, LocalDate.now().minusDays(1)));
    testGH.addGuess(new SingleGuess(true, LocalDate.now().minusDays(2), GameMode.MOVING));
    testGH.addGuess(new SingleGuess(false, LocalDate.now().minusDays(3), GameMode.TURBO));
    testGH.printBasicStats();

    //Input Data
    //IO.println("testing: "+ new CountryGuess(true, LocalDate.now(), "USA"));
    guessesAsDumbString = "10?*10***1?";
    String guessesAsCompressedString = "*?3*3*??2*";
    //input may be tested for format compliance by
    //if (isCompressedGuessString(guessesAsCompressedString))
    //if (isDumbString(guessesAsDumbString))
    
    //sanitize Input Data
    standardizeDumbString();

    //Main Menu
    do
    {
      IO.println("1 - show current guesses, 2 - add guess, 3 - success rates, 4 - streak stats, else - end");
      state = Integer.parseInt(IO.readln().trim());//5 - end; orElse: repeat menu
      switch (state)
      {
        case 1:
          browseGuesses();
          break;
        case 2:
          addGuess();
          break;
        case 3:
          showStatsSuccessRates();
          break;
        case 4:
          showStatsStreaks();
          break;
        default: state = 0;
      }
    }
    while (state > 0);
    System.out.println(" --- bye ---");   
  }
}