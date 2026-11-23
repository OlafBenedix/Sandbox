import java.time.*;

public class SingleGuess {
    private static int maxID = 0;
    private int guessID;
    private boolean success;
    private LocalDate guessDate;
//    LocalDateTime guessTimeStamp;
    private final GameMode gameMode;
    //country
    //points
    //guesstime

    public int getGuessID(){
        return guessID;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public boolean getSuccess(){
        return success;
    }

    public void setGuessDate(LocalDate guessDate){
        this.guessDate = guessDate;
    }
    
    public LocalDate getGuessDate(){
        return guessDate;
    }

    public GameMode getGameMode(){
        return gameMode;
    }

    public SingleGuess() {
        guessID = 0;
        success = false;
        guessDate = LocalDate.now();
        gameMode = GameMode.UNKNOWN;
    }

    public SingleGuess(boolean success, LocalDate guessDate) {
        guessID = ++maxID;
        this.success = success;
        this.guessDate = guessDate;
        gameMode = GameMode.UNKNOWN;
    }

        public SingleGuess(boolean success, LocalDate guessDate, GameMode gameMode) {
        guessID = ++maxID;
        this.success = success;
        this.guessDate = guessDate;
        this.gameMode = gameMode;
    }

    public String toString(){
        return
              super.toString()
              + "\nGuessID " + guessID
              + " in game mode " + gameMode
              + " of success " + success
              + " made on " + guessDate;
    }

}