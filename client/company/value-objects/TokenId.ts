import {ValueObject} from "../../shared/domain";
import {Result} from "../../shared/core";
import { generateId } from "../../shared/utils";

class TokenId extends ValueObject<string> {
    static readonly CANT_BE_EMPTY = "TokenId can't be empty";

    protected validate(value: string): void {
        if (!value) {
            throw new Error(TokenId.CANT_BE_EMPTY);
        }
    }

    static create(): Result<TokenId> {
        try {
            return Result.ok(new TokenId(generateId()));
        } catch (error) {
            return Result.fail(error.message);
        }
    }
}

export default TokenId;