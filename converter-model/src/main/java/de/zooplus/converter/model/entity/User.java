package de.zooplus.converter.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;

/**
 * Created by dragan
 */
@Entity
@Table(name = "users")
public class User extends AbstractEntity{

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic(optional = false)
    private Date dateOfBirth;

    @JsonIgnore
    @NotEmpty
    @Basic(optional = false)
    private String password;

    @Pattern(message = "{javax.validation.constraints.Email.message}", regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @Basic(optional = false)
    private String email;

    @NotEmpty
    @Basic(optional = false)
    private String address;

    @NotEmpty
    @Basic(optional = false)
    private String zipCode;

    @NotEmpty
    @Basic(optional = false)
    private String city;

    @NotEmpty
    @Basic(optional = false)
    private String country;

    @JsonIgnore
    @NotEmpty
    @Transient
    private String repeatedPassword;

    @JsonIgnore
    @OneToMany(mappedBy="user")
    private List<Conversion> conversions = new ArrayList<>();

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Conversion> getConversions() {
        return conversions;
    }

    public void setConversions(List<Conversion> conversions) {
        this.conversions = conversions;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

}
