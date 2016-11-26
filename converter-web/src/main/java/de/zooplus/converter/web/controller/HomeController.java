package de.zooplus.converter.web.controller;

import de.zooplus.converter.model.entity.Conversion;
import de.zooplus.converter.model.entity.User;
import de.zooplus.converter.service.external.ExchangeRateService;
import de.zooplus.converter.service.internal.ConversionService;
import de.zooplus.converter.service.internal.CurrencyService;
import de.zooplus.converter.service.internal.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Currency;
import java.util.List;


/**
 * Created by dragan
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private CurrencyService currencyService;


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getHome() {
        ModelAndView modelAndView = new ModelAndView("home", "conversion", new Conversion());
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createNewQuery(@ModelAttribute("conversion") @Valid Conversion conversion, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "home";
        }
        conversion.setRate(exchangeRateService.getExchangeRate(conversion.getSourceCurrency(), conversion.getTargetCurrency(), conversion.getValidOn()));
        conversionService.saveConversion(conversion, SecurityContextHolder.getContext().getAuthentication().getName());

        return "redirect:/home";
    }

    @ModelAttribute(value = "conversions")
    public List<Conversion> getConversions(){
        return conversionService.getAllForUser(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @ModelAttribute(value = "sourceCurrencies")
    public List<Currency> getSourceCurrencies(){
        return currencyService.getAllCurrencies();
    }

    @ModelAttribute(value = "targetCurrencies")
    public List<Currency> getTargetCurrencies(){
        return currencyService.getAllCurrencies();
    }

    @ModelAttribute(value = "loggedUser")
    public String getLoggedUser(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


}
