import java.time.*;
import java.util.ArrayList;

public class GuessHistory {
    private String playerName = "Test Player 1";
    private ArrayList<SingleGuess> guesses = new ArrayList<>();

    //Konstruktoren


    //guesses hinzufügen
    public void addGuess(SingleGuess guess) {
        guesses.add(guess);
    }

    public void addGuess(boolean success, LocalDate guessDate) {
        SingleGuess guess = new SingleGuess(success, guessDate);
        guesses.add(guess);
        //why not guesses.add(new SingleGuess(success, guessDate)); ?
    }

    public void addGuess(boolean success, LocalDate guessDate, GameMode gameMode) {
        SingleGuess guess = new SingleGuess(success, guessDate, gameMode);
        guesses.add(guess);
        //why not guesses.add(new SingleGuess(success, guessDate, gameMode)); ?
    }

    //Hilfsmethoden (intern?)
    private LocalDate getEarliestGuessDate() {
      //treatment of empty list of guesses? return null or throw exception?
        LocalDate earliestDate = guesses.get(0).getGuessDate();
        for (SingleGuess guess : guesses) {
            if (guess.getGuessDate().isBefore(earliestDate)) {
                earliestDate = guess.getGuessDate();
            }
        }
        return earliestDate;
    }

    private LocalDate getLatestGuessDate() {
      //treatment of empty list of guesses? return null or throw exception?
        LocalDate latestDate = guesses.get(0).getGuessDate();
        for (SingleGuess guess : guesses) {
            if (guess.getGuessDate().isAfter(latestDate)) {
                latestDate = guess.getGuessDate();
            }
        }
        return latestDate;
    }

    //Auswertungen erfragen
      public void printBasicStats() {
        IO.println("-------- Guess History of --------");
        IO.println("Player Name: " + playerName);
        IO.println("guesses made: " + guesses.size());
        IO.println("guesses from: " + getEarliestGuessDate() + " to " + getLatestGuessDate());
        //Name, Anzahl Guesses, Datum frühester + letzter Guess, ggf. Anzahl spezialisierter Guesses (Country, timed...)
      }

}