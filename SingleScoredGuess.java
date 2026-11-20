import java.time.*;
import java.util.OptionalDouble;

class SingleScoredGuess extends SingleGuess implements Scored {
    public final static double MINIMUM_SCORE = 0.0;
    public final static double MAXIMUM_SCORE = 5000.0;
    public final double CUTOFF_5K = 0.5; // 500 meters magic number; will be refactored later when grading curves are implemented
    private Double score;
    private Double distanceKm;

    public SingleScoredGuess(boolean success, LocalDate guessDate, GameMode gameMode, double score, double distanceKm) {
        super(success, guessDate, gameMode);
        this.score = Math.clamp(score, MINIMUM_SCORE, MAXIMUM_SCORE);
        this.distanceKm = Math.max(0, distanceKm);
    }

    public SingleScoredGuess(boolean success, LocalDate guessDate, double score, double distanceKm) {
        IO.print("testing..." + success + guessDate + GameMode.UNKNOWN + score + distanceKm);
        //SingleScoredGuess(success, guessDate, GameMode.UNKNOWN, score, distanceKm);
    }

    public static SingleScoredGuess withScoreAndDistance(boolean success, LocalDate guessDate, GameMode gameMode, double score, double distanceKm) {
        return new SingleScoredGuess(success, guessDate, gameMode, score, distanceKm);
    }

    public static SingleScoredGuess withScoreAndDistance(boolean success, LocalDate guessDate, double score, double distanceKm) {
        return new SingleScoredGuess(success, guessDate, GameMode.UNKNOWN, score, distanceKm);
    }

    public static SingleScoredGuess withScore(boolean success, LocalDate guessDate, GameMode gameMode, double score) {
        return new SingleScoredGuess(success, guessDate, gameMode, score, Double.NaN);
    }

    public static SingleScoredGuess withScore(boolean success, LocalDate guessDate, double score) {
        return new SingleScoredGuess(success, guessDate, GameMode.UNKNOWN, score, Double.NaN);
    }

    public static SingleScoredGuess withDistance(boolean success, LocalDate guessDate, GameMode gameMode, double distanceKm) {
        return new SingleScoredGuess(success, guessDate, gameMode, Double.NaN, distanceKm);
    }

    public static SingleScoredGuess withDistance(boolean success, LocalDate guessDate, double distanceKm) {
        return new SingleScoredGuess(success, guessDate, GameMode.UNKNOWN, Double.NaN, distanceKm);
    }

    @Override
    public double getExactScore() {
        //todo: display/log warning if score is null (when warning system has been implemented)
        return score;//.orElse(0.0);
    }

    @Override
    public int getOfficialScore() {
        //todo: display/log warning if score is null (when warning system has been implemented)
        return (int) Math.round(score);//.orElse(0.0));
    }

    @Override
    public double getDistanceKm() {
        //todo: display/log warning if distanceKm is null (when warning system has been implemented)
        return distanceKm;
    }

    @Override
    public double getEstimatedScoreFromDistance() {
        // example implementation - will be refactored later when grading curves are implemented
        if (distanceKm.isNaN())
            return 0.0;
        else if (distanceKm < CUTOFF_5K)
            return MAXIMUM_SCORE;
        else 
            return MAXIMUM_SCORE * Math.pow((1 - Math.min(distanceKm,10000.0)/10000.0),6);
    }

    @Override
    public double getEstimatedDistanceFromScore() {
        // Placeholder implementation - replace with actual estimation logic
        return 0.0;
    }

}