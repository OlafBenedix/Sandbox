import java.util.regex.*;
import java.time.*;

public class CountryGuess extends SingleGuess {
    private String country;

    public void setCountry(String country){
        if (   (country.length() == 3) 
            && Pattern.compile("[a-zA-Z]{3}").matcher(country).find()
           )
            this.country = country.toUpperCase();
        else
            {IO.println("irregular country code");
            this.country = "000";}
    }
    
    public String getCountry(){
        return country;
    }

    public CountryGuess() {
        super();
        setCountry("");
    }

    public CountryGuess(boolean success, LocalDate guessDate, String country) {
        super(success, guessDate);
        setCountry(country);
    }

    public String toString(){
        return super.toString()
               + " on a location in " + country;
    }
}