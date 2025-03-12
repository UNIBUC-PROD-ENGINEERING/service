package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// import ro.unibuc.hello.dto.Game;
import ro.unibuc.hello.dto.Subscription;
import ro.unibuc.hello.exception.EntityNotFoundException;
// import ro.unibuc.hello.service.GamesService;
import ro.unibuc.hello.service.SubscriptionService;
import ro.unibuc.hello.service.GreetingsService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class SubscriptionController {
    
    @Autowired
    private SubscriptionService subscriptionsService;

    @GetMapping("/subscriptions")
    @ResponseBody
    public List<Subscription> getAllSubscriptions() {
        return subscriptionsService.getAllSubscriptions();
    }


}
