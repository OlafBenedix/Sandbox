public class CountryStreak {
  static String streakAsDumbString;

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

  public static void main(String[] args) {
    streakAsDumbString = "1001*001";
    System.out.println("Your country streak is: " + streakAsDumbString);
    System.out.println("Your success rate is: " + successRateOfDumbString(streakAsDumbString));
  }
}