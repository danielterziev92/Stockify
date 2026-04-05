import { hashCode } from "../utils";

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