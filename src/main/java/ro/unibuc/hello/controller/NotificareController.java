package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.NotificareService;
import ro.unibuc.hello.model.Notificare;

import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
@RequestMapping("/api/notificare")
public class NotificareController {

    @Autowired
    private NotificareService notificareService;

    // @GetMapping("/notificare")
    // @ResponseBody
    // public List<Notificare> notificare(@RequestParam(name="eventId", required=false, defaultValue="0") String eventId) {
    //     return notificareService.getNotificariByEventId(eventId);
    // }

    @PostMapping("/addNotificare")
    @ResponseBody
    public Notificare createNotificare(@RequestBody Notificare notificare) {
        return notificareService.createNotificare(notificare);
    }

    @GetMapping("/notificariByEventId/{eventId}")
    @ResponseBody
    public List<Notificare> getNotificariByEventId(@PathVariable String eventId) {
        return notificareService.getNotificariByEventId(eventId);
    }

    @GetMapping("/notificariByUserId/{userId}")
    @ResponseBody
    public List<Notificare> getNotificareByUserId(@PathVariable String userId) {
        return notificareService.getNotificareByUserId(userId);
    }

    @PostMapping("/addNotificareByUserId/{userId}")
    @ResponseBody
    public void createNotificaribyUserId(@PathVariable String userId) {
        notificareService.createNotificaribyUserId(userId);
    }

    @PostMapping("/acceptInvitation/{notificareId}")
    @ResponseBody
    public void acceptInvitation(@PathVariable String notificareId) {
        notificareService.acceptInvitation(notificareId);
    }
   
}