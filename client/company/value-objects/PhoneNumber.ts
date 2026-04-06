import { ValueObject } from "../../shared/domain";
import { Result } from "../../shared/core";
import { validateUtils } from "../../shared/utils";

export class PhoneNumber extends ValueObject<string> {
    static readonly CANT_BE_EMPTY = "PhoneNumber can't be empty";
    static readonly INVALID_PHONE_NUMBER_ERROR = "Invalid phone number format";

    protected validate(value: string): void {
        value = value.trim();

        if (!value) {
            throw new Error(PhoneNumber.CANT_BE_EMPTY);
        }

        if (!validateUtils.isValidPhoneNumber(value)) {
            throw new Error(PhoneNumber.INVALID_PHONE_NUMBER_ERROR);
        }
    }

    static create(value: string): Result<PhoneNumber> {
        try {
            return Result.ok(new PhoneNumber(value.trim()));
        } catch (error) {
            return Result.fail(error.message);
        }
    }
}