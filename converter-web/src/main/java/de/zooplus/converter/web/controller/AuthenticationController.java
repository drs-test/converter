package de.zooplus.converter.web.controller;

import de.zooplus.converter.service.internal.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by dragan
 */
@Controller
@RequestMapping("/")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @RequestMapping
    public View redirectToLogin(){
        return new RedirectView("home");
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logout (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String getLogin(){
        return "login";
    }

}
