/**
 * This class defines a model for requests to adjust the difficulty level in TraMS.
 */
export class AdjustDifficultyLevelRequest {

    private company: string;
    private difficultyLevel: string;

    /**
     * Construct a new request to adjust the difficulty level.
     * @param company the company that we should adjust the difficulty level for.
     * @param difficultyLevel the new difficulty level that should be set.
     */
    constructor(company: string, difficultyLevel: string) {
        this.company = company;
        this.difficultyLevel = difficultyLevel;
    }
}