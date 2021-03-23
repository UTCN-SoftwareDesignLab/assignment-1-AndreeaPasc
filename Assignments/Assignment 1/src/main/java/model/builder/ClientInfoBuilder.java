package model.builder;

import model.ClientInfo;
import model.User;

public class ClientInfoBuilder {

    private ClientInfo clientInfo;

    public ClientInfoBuilder(){
        clientInfo = new ClientInfo();
    }

    public  ClientInfoBuilder setId(Long id){
        clientInfo.setId(id);
        return this;
    }
    public ClientInfoBuilder setName(String name){
        clientInfo.setName(name);
        return this;
    }

    public ClientInfoBuilder setIdentificationNumber(Long identificationNumber){
        clientInfo.setIdentificationNumber(identificationNumber);
        return this;
    }

    public ClientInfoBuilder setPersonalNumericalCode(Long personalNumericalCode){
        clientInfo.setPersonalNumericalCode(personalNumericalCode);
        return this;
    }

    public ClientInfoBuilder setAddress(String address){
        clientInfo.setAddress(address);
        return this;
    }

    public ClientInfoBuilder setPhoneNumber(Long phoneNumber){
        clientInfo.setPhoneNumber(phoneNumber);
        return this;
    }
    public ClientInfo build() {
        return clientInfo;
    }
}
