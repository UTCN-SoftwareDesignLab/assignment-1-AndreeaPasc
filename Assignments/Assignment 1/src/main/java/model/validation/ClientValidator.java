package model.validation;

import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ClientValidator {
    private final Client client;
    private final List<String> errors;

    private static final String PHONE_NUMBER_VALIDATION_REGEX = "^(\\+\\d{1,2}\\s?)?1?\\-?\\.?\\s?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$";

    public ClientValidator(Client client) {
        this.client = client;
        errors = new ArrayList<>();
    }

    public List<String> getErrors() {
        return errors;
    }

    private void validateIdNumber(Client client){
        int length = Long.toString(client.getIdentificationNumber()).length();
        if(length != 6)
            errors.add("Id number should have length 6");
    }

    private void validatePNC(Client client){
        int length = Long.toString(client.getPersonalNumericalCode()).length();
        if(length != 10)
            errors.add("Id number should have length 10");
    }

    private void validatePhoneNUmber(Client client){
        if (!Pattern.compile(PHONE_NUMBER_VALIDATION_REGEX).matcher(client.getPhoneNumber().toString()).matches()) {
            errors.add("Invalid phone number!");
        }
    }

    public boolean validate(){
        validateIdNumber(client);
        validatePNC(client);
        validatePhoneNUmber(client);
        return errors.isEmpty();
    }
}
