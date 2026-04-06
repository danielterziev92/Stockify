const isOnlyLetters = (value: string) => /^[A-Za-z]+$/.test(value);
const isValidPhoneNumber = (value: string) => /^\+\d{1,3}\d{9,12}$/.test(value);
const isValidUsername = (value: string) => /^[a-z0-9._]+$/.test(value);
const isValidPostcode = (value: string) => /^\d{3,5}$/.test(value);
const isValidStreet = (value: string) => /^(?=.*\p{L})[\p{L} -]+$/u.test(value);
const isValidBuildingNumber = (value: string) => /^[0-9]+[A-Za-z]?$/u.test(value);
const isValidApartment = (value: string) => /^[0-9]+[A-Za-z]?$/u.test(value);
const isvalidCardNumber = (value: string) => /^\d{3}-\d{2}-\d{4}$/.test(value);


export const validateUtils = {
    isOnlyLetters,
    isValidPhoneNumber,
    isValidUsername,
    isValidPostcode,
    isValidStreet,
    isValidBuildingNumber,
    isValidApartment,
    isvalidCardNumber,
};