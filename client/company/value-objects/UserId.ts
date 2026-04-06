import {ValueObject} from "../../shared/domain";
import { Result } from "../../shared/core";
import { generateId } from "../../shared/utils";

class UserId extends ValueObject<string> {
    static readonly CANT_BE_EMPTY = "UserId can't be empty";

    protected validate(value: string): void {
        if (!value) {
            throw new Error(UserId.CANT_BE_EMPTY);
        }
    }

    static create(): Result<UserId> {
        try {
            return Result.ok(new UserId(generateId()));
        } catch (error) {
            return Result.fail(error.message);
        }
    }
}

export default UserId;