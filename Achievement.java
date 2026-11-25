import java.time.*;

public record Achievement(String name, String playerName, int type, LocalDate date, String description) {
// commenting out the provided constructor for now, until the course covers exception handling
    // Constructor with validation
/*    public Achievement {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Achievement name cannot be null or empty");
        }
        if (playerName == null || playerName.isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be null or empty");
        }
        if (type < 0) {
            throw new IllegalArgumentException("Achievement type cannot be negative");
        }
        if (date == null) {
            throw new IllegalArgumentException("Achievement date cannot be null");
        }
        if (description == null) {
            throw new IllegalArgumentException("Achievement description cannot be null");
        }
    }
*/}