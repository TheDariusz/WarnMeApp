package com.thedariusz.warnme.api;

import com.thedariusz.warnme.MeteoAlertService;
import com.thedariusz.warnme.MeteoAlert;
import com.thedariusz.warnme.twitter.TweetService;
import com.thedariusz.warnme.user.UserDto;
import com.thedariusz.warnme.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/alerts")
public class MeteoAlertViewsController {

    private static final String HOME_VIEW = "index";
    private static final String LOGIN_VIEW = "login";
    private static final String LOGOUT_VIEW = "logout";
    private static final String TWITTER_VIEW = "twitter";
    private static final String ERROR_VIEW = "error";
    private static final String REGISTER_VIEW = "register";
    private static final String CONTACT_VIEW = "contact";

    private final UserService userService;
    private final TweetService tweetService;
    private final MeteoAlertService meteoAlertService;

    @Autowired
    public MeteoAlertViewsController(UserService userService, TweetService tweetService, MeteoAlertService meteoAlertService) {
        this.userService = userService;
        this.tweetService = tweetService;
        this.meteoAlertService = meteoAlertService;
    }

    @GetMapping
    public String getMainView(Model model) {
        List<MeteoAlert> meteoAlertsFromDb = meteoAlertService.getMeteoAlertsFromDb();
        String refreshDate = meteoAlertService.getRefreshDate();
        List<Post> posts = Post.preparePosts(meteoAlertsFromDb);

        model.addAttribute("posts", posts);
        model.addAttribute("refreshDate", refreshDate);
        return HOME_VIEW;
    }

    @GetMapping("/login")
    public String getLoginView() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return LOGIN_VIEW;
        }

        return "redirect:/alerts";
    }

    @GetMapping("/logout")
    public String getLogoutView() {
        return LOGOUT_VIEW;
    }

    @GetMapping("/twitter")
    public String getTwitterView() {
        return TWITTER_VIEW;
    }

    @GetMapping("/contact")
    public String getContactView() {
        return CONTACT_VIEW;
    }

    @GetMapping("/error")
    public String getErrorView() {
        return ERROR_VIEW;
    }


    @GetMapping("/register")
    public String getRegisterView(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return REGISTER_VIEW;
    }
    @PostMapping("/register")
    public String getRegisterForm(@Valid UserDto userDto, BindingResult bindingResult, Model model) {
        if (userService.existUser(userDto)) {
            bindingResult.rejectValue("username", "error.user",
                    "User '"+userDto.getUsername()+"' is already register");
            return REGISTER_VIEW;
        }

        if (bindingResult.hasErrors()) {
            return REGISTER_VIEW;
        } else {
            userService.saveUser(userDto);
            return LOGIN_VIEW;
        }
    }

    @GetMapping("/refresh/{id}")
    public String getNewTweets(@PathVariable("id") String twitterUserId) {
        tweetService.syncTweets(twitterUserId);
        return "redirect:/alerts";
    }

}
