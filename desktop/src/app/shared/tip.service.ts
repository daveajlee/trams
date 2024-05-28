import {Injectable} from "@angular/core";

@Injectable()
/**
 * This class manages the tips that are currently defined and provides a random tip if required.
 */
export class TipService {

    private tips: string[] = [
        "Tip: Watch your balance! You can't buy new vehicles or run more routes if you don't have money!",
        "Tip: Earn money by improving your passenger satisfaction through running vehicles on time!",
        "Tip: If your passenger satisfaction falls too low, you may be forced to resign!"
    ];

    /**
     * Choose a random tip and return the content to the user.
     */
    getRandomTip(): string {
        return this.tips[Math.floor(Math.random() * this.tips.length)];
    }

}