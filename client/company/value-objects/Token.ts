import {Result} from "../../shared/core";
import { ValueObject } from "../../shared/domain";

class Token extends ValueObject<string> {
    static readonly CANT_BE_EMPTY = "Token can't be empty";

    protected validate(value: string): void {
        if (!value) {
            throw new Error(Token.CANT_BE_EMPTY);
        }
    }

    static create(value: string): Result<Token> {
        try {
            return Result.ok(new Token(value));
        } catch (error) {
            return Result.fail(error.message);
        }
    }
}

export default Token;