import java.time.*;
import java.util.ArrayList;

public class GuessHistory {
    private String playerName;
    private ArrayList<SingleGuess> guesses = new ArrayList<>();

    //Konstruktoren
    public GuessHistory() {
        playerName = "Test Player 1";
    }

    public GuessHistory(String playerName) {
        this.playerName = playerName;
    }

    public GuessHistory(String playerName, SingleGuess guess) {
        this.playerName = playerName;
        guesses.add(guess);
    }
    
    //guesses hinzufügen
    public void addGuess(SingleGuess guess) {
        guesses.add(guess);
    }

    public void addGuess(boolean success, LocalDate guessDate) {
        SingleGuess guess = new SingleGuess(success, guessDate);
        guesses.add(guess);
        //why not guesses.add(new SingleGuess(success, guessDate)); ?
    }

    //later: Constructor with file name to read guesses from

    public void addGuess(boolean success, LocalDate guessDate, GameMode gameMode) {
        SingleGuess guess = new SingleGuess(success, guessDate, gameMode);
        guesses.add(guess);
        //why not guesses.add(new SingleGuess(success, guessDate, gameMode)); ?
    }

    //Hilfsmethoden (intern?)
    private void sortGuessesByDate() {
        guesses.sort((g1, g2) -> g1.getGuessDate().compareTo(g2.getGuessDate()));
    }

    private void sortGuessesByDateThenID() {
        guesses.sort((g1, g2) -> {
            int dateComparison = g1.getGuessDate().compareTo(g2.getGuessDate());
            if (dateComparison != 0) {
                return dateComparison;
            } else {
                return Integer.compare(g1.getGuessID(), g2.getGuessID());
            }
        });
    }

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

      public void printOneGuessBasic(int index) {
        if (index < 0 || index >= guesses.size()) {
            IO.println("Invalid index for guess.");
            return;
        }
        IO.println(guesses.get(index).getBasicInfo());
      }

      public void printOneGuessDetailed(int index) {
        if (index < 0 || index >= guesses.size()) {
            IO.println("Invalid index for guess.");
            return;
        }
        IO.println(guesses.get(index).getDetailedInfo());
      }


      public void peekPreview(int startIndex, int endIndex) {
        if (startIndex > endIndex)
            return;
        if (startIndex < 0 || endIndex > guesses.size() || startIndex >= endIndex) {
            IO.println("Invalid range for preview.");
            return;
        }
        for (int i = startIndex; i <= endIndex; i++) {
            //IO.println(guesses.get(i).toString());
            printOneGuessDetailed(i);
        }
    }

}