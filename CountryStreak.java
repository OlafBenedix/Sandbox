import java.util.ArrayList;
//import org.apache.commons.statistics.variance;

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
    switch(type){
      case StatType.LONGEST: 
      {
        int best = 0;
        for( int i=0; i<seriesOfStreaks.size(); i++ )
          if ( seriesOfStreaks.get(i).intValue() > best )
            best = seriesOfStreaks.get(i).intValue();
        return best;
      }
      case StatType.CURRENT:
        {
          if (guessesAsDumbString.endsWith("1") || guessesAsDumbString.endsWith("*"))
            return seriesOfStreaks.getLast().intValue();
          else
            return 0;
        }
    }
    return -1;
  }

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
    guessesAsDumbString = "10010***1?";
    System.out.println("Your country streak is: " + guessesAsDumbString);
    System.out.println("Your success rate is: " + successRateOfDumbString(guessesAsDumbString));
    inferPartialSuccessRates(5);
    System.out.println("in chunks of 5 guesses, your success rate developed as follows:");
    System.out.println(timeLineSuccessRate);
    inferSeriesOfStreaks();
    System.out.println("you had the following streaks:");
    System.out.println(seriesOfStreaks);
    System.out.println("your longest streak was:");
    System.out.println((int)getGuessingStats(StatType.LONGEST));
    System.out.println("your streak average was:");
    System.out.println(getGuessingStats(StatType.MEAN));
    System.out.println("your median streak was:");
    System.out.println(getGuessingStats(StatType.MEDIAN));
    System.out.println("your current streak is at:");
    System.out.println(getGuessingStats(StatType.CURRENT));

  }
}