interface Scored {
    double getExactScore();
    int getOfficialScore();
    double getEstimatedScoreFromDistance();
    double getDistanceKm();
    double getEstimatedDistanceFromScore();
}