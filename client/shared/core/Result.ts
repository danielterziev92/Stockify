export class Result<T> {
    private readonly _isSuccess: boolean;
    private readonly _error: string;
    private readonly _value: T;

    private constructor(isSuccess: boolean, error?: string, value?: T) {
        this._isSuccess = isSuccess;
        this._error = error || '';
        this._value = value as T;

        Object.freeze(this);
    }

    public static ok<U>(value: U): Result<U> {
        return new Result<U>(true, undefined, value);
    }

    public static fail<U>(error: string): Result<U> {
        return new Result<U>(false, error);
    }

    public get isSuccess(): boolean {
        return this._isSuccess;
    }

    public get isFailure(): boolean {
        return !this._isSuccess;
    }

    public getValue(): T {
        if (!this._isSuccess) {
            throw new Error("Can't get the value of a failed result");
        }
        return this._value;
    }

    public getError(): string {
        if (this._isSuccess) {
            throw new Error("Can't get the error of a successful result");
        }
        return this._error;
    }
}