import {ValueObject} from "../../shared/domain";
import {CountryId, Name} from "../value-objects";
import Entity from "../../shared/domain/Entitiy";
import {Result} from "../../shared/core";

class Country extends Entity<CountryId> {
    private readonly CountryId: CountryId;
    private readonly Name: Name;

    constructor(countryId,name: Name) {
        super(CountryId.getValue());
        this.CountryId = CountryId;
        this.Name = name;
    }

    static create(CountryId: CountryId, name: Name): Result<Country> {
        try {
            const country = new Country(CountryId.create().getValue(), Name.create(name).getValue());
            return Result.ok<Country>(country);
        } catch (error) {
            return Result.fail<Country>(error.message);
        }
    }

    //Getters
    get countryIdValue(): CountryId {
        return this.CountryId;
    }

    get nameValue(): Name {
        return this.Name;
    }
}

export default Country;