import { Entity } from "../../shared/domain";
import {Result} from "../../shared/core";

import { ProfileId, Name, PhoneNumber } from "../value-objects";

export default class Profile extends Entity {
    private readonly profileId: ProfileId;
    private readonly firstName: Name;
    private readonly lastName: Name;
    private readonly createdAt: Date = new Date();

    private middleName?: Name;
    private phoneNumber?: PhoneNumber;
    private updatedAt: Date = new Date();

    constructor(
        profileId: ProfileId,
        firstName: Name,
        lastName: Name,
        middleName?: Name,
        phoneNumber?: PhoneNumber,
    ) {
        super(profileId.getValue());
        this.profileId = profileId;
        this.firstName = firstName;
        this.lastName = lastName;

        this.middleName = middleName;
        this.phoneNumber = phoneNumber;
    }

    static create(
        firstName: Name,
        lastName: Name,
        middleName?: Name,
        phoneNumber?: PhoneNumber,
    ) {
        try {
            const profile = new Profile(
                ProfileId.create().getValue(),
                Name.create(firstName).getValue(),
                Name.create(lastName).getValue(),
                middleName ? Name.create(middleName).getValue() : undefined,
                phoneNumber ? PhoneNumber.create(phoneNumber).getValue() : undefined,
            );

            return Result.ok<Profile>(profile);

        } catch (error) {
            return Result.fail<Profile>(error.message);
        }

    }

    //Getters
    get profileIdValue(): ProfileId {
        return this.profileId;
    }

    get firstNameValue(): Name {
        return this.firstName;
    }

    get lastNameValue(): Name {
        return this.lastName;
    }

    get middleNameValue(): Name | undefined {
        return this.middleName;
    }

    get phoneNumberValue(): PhoneNumber | undefined {
        return this.phoneNumber;
    }

    get createAtValue(): Date {
        return this.createdAt;
    }

    get updatedAtValue(): Date {
        return this.updatedAt;
    }

    //Setters for optional fields
    setMiddleName(middleName: string): void {
        this.middleName = Name.create(middleName).getValue();
    }

    setPhoneNumber(phoneNumber: string): void {
        this.phoneNumber = PhoneNumber.create(phoneNumber).getValue();
    }

    setUpdateAt(): void {
        this.updatedAt = new Date();
    }
}