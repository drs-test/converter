package de.zooplus.converter.web.controller;

import de.zooplus.converter.model.entity.User;
import de.zooplus.converter.service.internal.CountryService;
import de.zooplus.converter.service.internal.UserService;
import de.zooplus.converter.web.validation.PasswordValidator;
import de.zooplus.converter.web.validation.UniqueEmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

/**
 * Created by dragan
 */
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private PasswordValidator passwordValidator;

    @Autowired
    private UniqueEmailValidator uniqueEmailValidator;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getRegistration(){
        ModelAndView modelAndView = new ModelAndView("registration", "user", new User());
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String register(@ModelAttribute("user") @Valid User user, BindingResult result){

        uniqueEmailValidator.validate(user, result);
        passwordValidator.validate(user, result);

        if(result.hasErrors()){
            return "registration";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "registrationSuccess";
    }

    @ModelAttribute(value = "countries")
    public List<Locale> getCountries(){
        return countryService.getAllCountries();
    }

}
