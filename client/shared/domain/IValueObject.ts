export interface IValueObject<T> {
    getValue(): T;
    equals(other: IValueObject<T>): boolean;
    toString(): string;
}