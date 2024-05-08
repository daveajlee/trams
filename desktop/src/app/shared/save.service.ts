import {Injectable} from '@angular/core';
import {GameService} from "./game.service";

@Injectable()
/**
 * This class saves a file containing game information.
 */
export class SaveService {

    gameService: GameService;

    constructor(private gameService2: GameService) {
        this.gameService = gameService2;
    }

    /**
     * Save a json file.
     * @param file the file system file handle to save to.
     */
    async onSaveFile(file: FileSystemFileHandle): Promise<void> {
        // Create object to write to.
        const writeable = await file.createWritable();
        // Write the actual contents of the file via JSON.
        await writeable.write(JSON.stringify(this.gameService.getGame()));
        // Close the file to ensure it is written to disk.
        await writeable.close();
    }

}
