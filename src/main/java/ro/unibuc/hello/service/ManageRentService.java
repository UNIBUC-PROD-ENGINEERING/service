package ro.unibuc.hello.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.Rent;
import ro.unibuc.hello.data.RentRepository;
import ro.unibuc.hello.dto.LateRent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManageRentService {
    private final RentRepository rentRepository;
    private final int LATE_DAYS = 7;

    @Autowired
    public ManageRentService(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    public List<Rent> getAllActiveRents() {
        return rentRepository.findAll().stream()
                .filter(rent -> !rent.isReturned())
                .collect(Collectors.toList());
    }

    public List<LateRent> getLateRents() {
        LocalDateTime lateThreshold = LocalDateTime.now().minusDays(LATE_DAYS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return rentRepository.findAll().stream()
                .filter(r -> !r.isReturned() && r.getRentDate().isBefore(lateThreshold))
                .map(r -> new LateRent(r.getUserId(), r.getGameId(), r.getRentDate().format(formatter)))
                .collect(Collectors.toList());
    }
}
