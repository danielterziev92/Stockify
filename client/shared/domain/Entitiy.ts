import { hashCode} from "../utils/hashCode";

export abstract class Entity {
    protected readonly id: string;

    constructor(id: string) {
        this.id = id;
    }

    hashCode(): number {
        return hashCode(this.id);
    }

    equals(other: Entity): boolean {
        return this.hashCode() === other.hashCode();
    }
}