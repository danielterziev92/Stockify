import Result from "../../shared/core/Result";
import Entity from "../../shared/domain/Entitiy";
import { UserId, Email, Password } from "../value-objects";

export default class User extends Entity {
    private readonly userId: UserId;
    private readonly email: Email;
    private readonly password: Password;

    private  isVerified: boolean;
    private  isActive: boolean;

    constructor(userId: UserId, email: Email, password: Password) {
        super(userId.getValue());
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.isVerified = false;
        this.isActive = false;
    }

    public static create(
        email: Email,
        password: Password
    ): Result<User> {
        try {
            const newUser = new User(
                UserId.create().getValue(),
                Email.create(email).getValue(),
                Password.create(password).getValue()
            );
            return Result.ok(newUser);
        } catch (error) {
            return Result.fail(error.message);
        }
    }

    //getters
    get userIdValue(): UserId {
        return this.userId;
    }

    get emailValue(): Email {
        return this.email;
    }

    get passwordValue(): Password {
        return this.password;
    }

    get isVerifiedValue(): boolean {
        return this.isVerified;
    }

    get isActiveValue(): boolean {
        return this.isActive;
    }

    //setters
    setVerified(): void {
        this.isVerified = true;
    }

    setActive(): void {
        this.isActive = true;
    }
}