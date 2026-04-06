import {ValueObject} from "../../shared/domain";
import { Result } from "../../shared/core";
import { validateUtils } from "../../shared/utils";

export default class Password extends ValueObject<string> {
    static readonly CANT_BE_EMPTY = "Password can't be empty";
    static readonly INVALID_PASSWORD_ERROR = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one number";

    protected validate(value: string): void {
        value = value.trim();

        if (!value) {
            throw new Error(Password.CANT_BE_EMPTY);
        }

        if (!validateUtils.isValidPassword(value)) {
            throw new Error(Password.INVALID_PASSWORD_ERROR);
        }
    }

    static create(value: string): Result<Password> {
        try {
            return Result.ok(new Password(value.trim()));
        } catch (error) {
            return Result.fail(error.message);
        }
    }
}