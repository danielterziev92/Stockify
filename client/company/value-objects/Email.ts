import { Result } from "../../shared/core";
import { validateUtils } from "../../shared/utils";
import { ValueObject } from "../../shared/domain";

class Email extends ValueObject<string> {
    static readonly CANT_BE_EMPTY = "Email can't be empty";
    static readonly INVALID_EMAIL_ERROR = "Invalid email format";

    protected validate(value: string): void {
        value = value.trim();

        if (!value) {
            throw new Error(Email.CANT_BE_EMPTY);
        }

        if (!validateUtils.isValidEmail(value)) {
            throw new Error(Email.INVALID_EMAIL_ERROR);
        }
    }

    static create(value: string): Result<Email> {
        try {
            return Result.ok(new Email(value.trim()));
        } catch (error) {
            return Result.fail(error.message);
        }
    }

}

export default Email;