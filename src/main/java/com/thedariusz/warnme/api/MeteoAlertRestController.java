package com.thedariusz.warnme.api;

import com.thedariusz.warnme.twitter.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/alerts")
public class MeteoAlertRestController {

    private final TweetService tweetService;

    @Autowired
    public MeteoAlertRestController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping("/{id}")
    public void fetchAllAlerts(@PathVariable("id") String twitterUserId) {
        tweetService.syncTweets(twitterUserId);
    }

    @GetMapping("/version")
    public String test(){
        return "This is newest version!";
    }

}
