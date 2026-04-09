import {Result} from "../../shared/core";
import {Entity} from "../../shared/domain"
import { TokenId, Token, UserId} from "../value-objects";

class VarificationToken extends Entity<string> {
    private readonly tokenId: TokenId;
    private readonly token: Token;
    private readonly createdAt: Date = new Date();
    private readonly userId: UserId;

    private expiresAt: Date;

    constructor(
        tokenId: TokenId,
        token: Token,
        userId: UserId,
        expiresAt: Date,
    ) {
        super(tokenId.getValue());
        this.tokenId = tokenId;
        this.token = token;
        this.userId = userId;
    }

    public static create (
        token: Token,
        userId: UserId,
        expiresAt: Date,
    ): Result<VarificationToken> {
        try {
            const varificationToken = new VarificationToken(
                TokenId.create().getValue(),
                Token.create(token.getValue()).getValue(),
                UserId.create().getValue(),
                expiresAt,
            );

            return Result.ok<VarificationToken>(varificationToken);
        } catch (error) {
            return Result.fail<VarificationToken>(error.message);
        }
    }

    //Getters
    get tokenIdValue(): TokenId {
        return this.tokenId;
    }

    get tokenValue(): Token {
        return this.token;
    }

    get userIdValue(): UserId {
        return this.userId;
    }

    get expiresAtValue(): Date {
        return this.expiresAt;
    }

    //Setters
    setExpiresAt(expiresAt: Date): void {
        this.expiresAt = expiresAt;
    }
}

export default VarificationToken;