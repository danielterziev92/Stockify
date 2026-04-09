import {ValueObject} from "../../shared/domain";
import { Result } from "../../shared/core";
import { generateId } from "../../shared/utils";

class CountryId extends ValueObject<string> {
    static readonly CANT_BE_EMPTY = "CountryId can't be empty";

    protected validate(value: string): void {
        if (!value) {
            throw new Error(CountryId.CANT_BE_EMPTY);
        }
    }

    static create(): Result<CountryId> {
        try {
            return Result.ok(new CountryId(generateId()));
        } catch (error) {
            return Result.fail(error.message);
        }
    }
}

export default CountryId;