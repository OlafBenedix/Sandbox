import java.time.*;

public class SingleTimedGuess extends SingleGuess implements Timed {
    private double guessTime;
    private double allottedTime;

    public SingleTimedGuess(boolean success, LocalDate guessDate, GameMode gameMode, double guessTime, double allottedTime) {
        super(success, guessDate, gameMode);
        this.guessTime = guessTime;
        this.allottedTime = allottedTime;
    }

    public void setGuessTime(double guessTime) {
        this.guessTime = guessTime;
    }

    public double getGuessTime() {
        return guessTime;
    }

    public void setAllottedTime(double allottedTime) {
        this.allottedTime = allottedTime;
    }

    public double getAllottedTime() {
        return allottedTime;
    }

    public boolean didGuessTimeRunOut() {
        return guessTime >= allottedTime;
    }
}