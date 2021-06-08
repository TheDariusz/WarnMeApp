package com.thedariusz.warnme.api;

import com.thedariusz.warnme.MeteoAlertService;
import com.thedariusz.warnme.twitter.MeteoAlert;
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
import java.time.OffsetDateTime;
import java.util.List;

@Controller
@RequestMapping(value = "/alerts")
public class MeteoAlertViewsController {

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
        return "index";
    }

    @GetMapping("/login")
    public String getLoginView() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }

        return "redirect:/alerts";
    }

    @GetMapping("/logout")
    public String getLogoutView() {
        return "logout";
    }

    @GetMapping("/twitter")
    public String getTwitterView() {
        return "twitter";
    }

    @GetMapping("/error")
    public String getErrorView() {
        return "error";
    }


    @GetMapping("/register")
    public String getRegisterView(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "register";
    }
    @PostMapping("/register")
    public String getRegisterForm(@Valid UserDto userDto, BindingResult bindingResult, Model model) {
        String message = userService.validateUsername(userDto); //todo change to pro validation
        if (!message.isBlank()) {
            bindingResult.rejectValue("username", "error.user",
                    message);
            return "register";
        }

        message = userService.validatePassword(userDto); //todo change to pro validation
        if (!message.isBlank()) {
            bindingResult.rejectValue("password", "error.password",
                    message);
            return "register";
        }

        if (bindingResult.hasErrors()) {
            return "register";
        } else {
            userService.saveUser(userDto);
            return "login";
        }
    }

    @GetMapping("/refresh/{id}")
    public String getNewTweets(@PathVariable("id") String twitterUserId) {
        tweetService.syncTweets(twitterUserId);
        return "redirect:/alerts";
    }

}
