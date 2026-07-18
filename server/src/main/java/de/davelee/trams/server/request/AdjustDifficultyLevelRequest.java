package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request to adjust the difficulty level
 * for a particular company.
 * @author Dave Lee
 */
public class AdjustDifficultyLevelRequest {

    /**
     * The name of the company to adjust the difficulty level for.
     */
    private String company;

    /**
     * The new difficulty level which should be used for this company (can be EASY, MEDIUM or HARD).
     */
    private String difficultyLevel;

    public AdjustDifficultyLevelRequest() {
    }

    public AdjustDifficultyLevelRequest(String company, String difficultyLevel) {
        this.company = company;
        this.difficultyLevel = difficultyLevel;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    @Override
    public String toString() {
        return "AdjustDifficultyLevelRequest{" +
                "company='" + company + '\'' +
                ", difficultyLevel='" + difficultyLevel + '\'' +
                '}';
    }
}
