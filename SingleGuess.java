import java.time.*;

public class SingleGuess {
    private int guessID;
    private boolean success;
    private LocalDate guessDate;
//    LocalDateTime guessTimeStamp;
    //country
    //points
    //guesstime

    int getGuessID(){
        return guessID;
    }

    void setSuccess(boolean success){
        this.success = success;
    }

    boolean getSuccess(){
        return success;
    }

    void setGuessDate(LocalDate guessDate){
        this.guessDate = guessDate;
    }
    
    LocalDate getGuessDate(){
        return guessDate;
    }

    SingleGuess() {
        guessID = 0;
        success = false;
        guessDate = LocalDate.now();
    }

    SingleGuess(boolean success, LocalDate guessDate) {
        guessID = 0;
        this.success = success;
        this.guessDate = guessDate;
    }
}
//printfunktion toString von Objekt überladen?