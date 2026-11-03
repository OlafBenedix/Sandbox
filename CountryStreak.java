import java.util.ArrayList;

public class CountryStreak {
  static String streakAsDumbString;
  static ArrayList<Double> timeLineSuccessRate = new ArrayList<Double>();

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
    if ( partialStreakLength > 0  &&  partialStreakLength <= streakAsDumbString.length() )
    {
      for(int i=0; i <= streakAsDumbString.length() - partialStreakLength; i++ )
      {
        timeLineSuccessRate.add( successRateOfPartialString( streakAsDumbString, i, i+partialStreakLength ));
      }
    }

  }

  public static void main(String[] args) {
    streakAsDumbString = "10010***10";
    System.out.println("Your country streak is: " + streakAsDumbString);
    System.out.println("Your success rate is: " + successRateOfDumbString(streakAsDumbString));
    inferPartialSuccessRates(5);
    System.out.println("in chunks of 5 guesses, your success rate developed as follows:");
    System.out.println(timeLineSuccessRate);
  }
}