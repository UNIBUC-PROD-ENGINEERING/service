package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.model.Event;
import ro.unibuc.hello.model.Notificare;
import ro.unibuc.hello.model.User;
import ro.unibuc.hello.repository.NotificareRepository;
import ro.unibuc.hello.repository.UserRepository;
import ro.unibuc.hello.service.EventService;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class NotificareService {
    @Autowired
    private final NotificareRepository notificareRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final EventService eventService;

    
    public NotificareService(NotificareRepository notificareRepository, UserRepository userRepository, EventService eventService) {
        this.notificareRepository = notificareRepository;
        this.userRepository = userRepository;
        this.eventService = eventService;
    }

    public List<Notificare> getNotificariByEventId(String eventId) {
        return notificareRepository.findByEventId(eventId);
    }

    public List<Notificare> getNotificareByUserId(String userId) {
        return notificareRepository.findByUserId(userId);
    }

    public Notificare createNotificare(Notificare notificare) {
        return notificareRepository.save(notificare);
    }

    public void createNotificaribyUserId(String userId) {
        List<Notificare> notificari = notificareRepository.findByUserId(userId);

        eventService.getEventsByUserId(userId).forEach(event -> {
            boolean hasNotification = notificari.stream()
                .anyMatch(notificare -> notificare.getEventId().equals(event.getEventId()));
            if (!hasNotification) {
                Notificare notificare = new Notificare();
                notificare.setEventId(event.getEventId());
                notificare.setUserId(userId);
                notificare.setTipVerificare("creator");
                notificare.setVerificare(true);
                createNotificare(notificare);

                for (String username : event.getUsernames()) {
                    Notificare notificareInvitat = new Notificare();
                    notificareInvitat.setEventId(event.getEventId());
                    userRepository.findByUsername(username).ifPresent(user -> {
                        notificareInvitat.setUserId(user.getId());
                    });
                    notificareInvitat.setTipVerificare("invitat");
                    notificareInvitat.setVerificare(false);
                    createNotificare(notificareInvitat);
                }
            }
        });
    }

    public List<Notificare> getNotificariByUserId(String userId) {
        return notificareRepository.findByUserId(userId);
    }

    public Notificare acceptInvitation(String notificareId) {
        Notificare notificare = notificareRepository.findByNotificareId(notificareId);
        if (notificare != null) {
            notificare.setVerificare(true);
            Notificare savedNotificare = notificareRepository.save(notificare);
            return savedNotificare;
        }
        return notificare;
    }

    public void deleteAllNotificari() {
        notificareRepository.deleteAll();
    }
}
