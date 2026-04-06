import { Result} from "../../shared/core";
import { ValueObject } from "../../shared/domain";
import { validateUtils } from "../../shared/utils";

export default class Name extends ValueObject<string> {
    static readonly CANT_BE_EMPTY = "Name can't be empty";
    static readonly MAX_LENGTH = 20;
    static readonly MIN_LENGTH = 2;
    static readonly INVALID_LENGTH_ERROR = `Name must be between ${this.MIN_LENGTH} and ${this.MAX_LENGTH} characters`;
    static readonly INVALID_CHARACTERS_ERROR = 'Name must contain only letters';

    protected validate(value: string): void {
        value = value.trim();

        if (!value) {
            throw new Error(Name.CANT_BE_EMPTY);
        }

        if (value.length < Name.MIN_LENGTH || value.length > Name.MAX_LENGTH) {
            throw new Error(Name.INVALID_LENGTH_ERROR);
        }

        if (!validateUtils.isOnlyLetters(value)) {
            throw new Error(Name.INVALID_CHARACTERS_ERROR);
        }
    }

    static create(value: string): Result<Name> {
        try {
            value = value.trim();
            const capitalizedValue = value.charAt(0).toUpperCase() + value.slice(1);
            return Result.ok(new Name(capitalizedValue));
        } catch (error) {
            return Result.fail(error.message);
        }
    }

}
