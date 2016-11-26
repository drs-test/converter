package de.zooplus.converter.dao.model.builder;

import de.zooplus.converter.model.entity.User;

import java.util.Date;

/**
 * Created by dragan
 */
public class UserBuilder {

    private final User user = new User();


    public UserBuilder setPassword(String password) {
        user.setPassword(password);
        return this;

    }

    public UserBuilder setEmail(String email) {
        user.setEmail(email);
          return this;
    }



    public UserBuilder setDateOfBirth(Date dateOfBirth) {
        user.setDateOfBirth(dateOfBirth);
        return this;
    }



    public UserBuilder setAddress(String address) {
       user.setAddress(address);
        return this;
    }



    public UserBuilder setZipCode(String zipCode) {
        user.setZipCode(zipCode);
        return this;
    }



    public UserBuilder setCity(String city) {
        user.setCity(city);
          return this;
    }



    public UserBuilder setCountry(String country) {
       user.setCountry(country);
        return this;
    }

    public User build(){
    return user;
    }
}