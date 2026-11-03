import java.util.ArrayList;

public class CountryStreak {
  static String guessesAsDumbString;
  static ArrayList<Double> timeLineSuccessRate = new ArrayList<Double>();
  static ArrayList<Integer> seriesOfStreaks = new ArrayList<Integer>();

  static double successRateOfDumbString(String s)
  {
    int hits = 0;
    for(int i=0;i<s.length();i++)
    {
      if ((s.charAt(i) == '1') || (s.charAt(i) == '*'))
      {
        hits ++;
      }
    }
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
    {
      for(int i=0; i <= guessesAsDumbString.length() - partialStreakLength; i++ )
      {
        timeLineSuccessRate.add( successRateOfPartialString( guessesAsDumbString, i, i+partialStreakLength ));
      }
    }
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

  public static void main(String[] args) {
    guessesAsDumbString = "10010***?1";
    System.out.println("Your country streak is: " + guessesAsDumbString);
    System.out.println("Your success rate is: " + successRateOfDumbString(guessesAsDumbString));
    inferPartialSuccessRates(5);
    System.out.println("in chunks of 5 guesses, your success rate developed as follows:");
    System.out.println(timeLineSuccessRate);
    inferSeriesOfStreaks();
    System.out.println("you had the following streaks:");
    System.out.println(seriesOfStreaks);
  }
}