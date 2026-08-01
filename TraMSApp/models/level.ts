class Level {

    name: string;
    description: string;
    selected: boolean;

    constructor(name: string, description: string, selected: boolean) {
        this.name = name;
        this.description = description;
        this.selected = selected;
    }

}

export default Level;