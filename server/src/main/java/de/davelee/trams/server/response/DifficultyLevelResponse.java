package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents a response containing the company
 * and the current difficulty level.
 * @author Dave Lee
 */
public class DifficultyLevelResponse {

    /**
     * The name of the company.
     */
    private String company;

    /**
     * The difficulty level of the company (can be EASY, MEDIUM or HARD).
     */
    private String difficultyLevel;

    public DifficultyLevelResponse() {
    }

    public DifficultyLevelResponse(String company, String difficultyLevel) {
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
        return "DifficultyLevelResponse{" +
                "company='" + company + '\'' +
                ", difficultyLevel='" + difficultyLevel + '\'' +
                '}';
    }
}
