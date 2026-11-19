import java.time.*;

class SingleScoredGuess extends SingleGuess implements Scored {
    public final static double MINIMUM_SCORE = 0.0;
    public final static double MAXIMUM_SCORE = 5000.0;
    public final double CUTOFF_5K = 0.5; // 500 meters magic number; will be refactored later when grading curves are implemented
    private double score;
    private double distanceKm;

    public SingleScoredGuess(boolean success, LocalDate guessDate, GameMode gameMode, double score, double distanceKm) {
        super(success, guessDate, gameMode);
        this.score = Math.clamp(score, MINIMUM_SCORE, MAXIMUM_SCORE);
        this.distanceKm = Math.max(0, distanceKm);
    }

    @Override
    public double getExactScore() {
        return score;
    }

    @Override
    public int getOfficialScore() {
        return (int) Math.round(score);
    }

    @Override
    public double getDistanceKm() {
        return distanceKm;
    }

    @Override
    public double getEstimatedScoreFromDistance() {
        // example implementation - will be refactored later when grading curves are implemented
        if (distanceKm < CUTOFF_5K)
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