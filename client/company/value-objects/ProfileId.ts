import { Result } from "../../shared/core";
import { ValueObject } from "../../shared/domain";
import { generateId } from "../../shared/utils";

class ProfileId extends ValueObject<string> {
    static readonly CANT_BE_EMPTY = "ProfileId can't be empty";

    protected validate(value: string): void {
        if (!value) {
            throw new Error(ProfileId.CANT_BE_EMPTY);
        }
    }

    static create(): Result<ProfileId> {
        try {
            return Result.ok(new ProfileId(generateId()));
        } catch (error) {
            return Result.fail(error.message);
        }
    }
}

export default ProfileId;