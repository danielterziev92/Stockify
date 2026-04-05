export abstract class ValueObject<T> {
    protected value: T;

    constructor(value: T) {
        this.validate(value);
        this.value = value;
    }

    protected abstract validate(value: T): void;

    public getValue(): T {
        return this.value;
    }

    public equals(other: ValueObject<T>): boolean {
        return (
            other.constructor === this.constructor &&
            this.value === other.value
        );
    }

    public toString(): string {
        return String(this.value);
    }
}