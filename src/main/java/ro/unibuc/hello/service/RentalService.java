package ro.unibuc.hello.service;

import ro.unibuc.hello.data.Rental;
import ro.unibuc.hello.data.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    @Autowired
    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public Rental rentGame(String userId, String gameId) {
        Optional<Rental> existingRental = rentalRepository.findByUserIdAndGameIdAndReturnDateIsNull(userId, gameId);
        if (existingRental.isPresent()) {
            throw new IllegalStateException("User already rented this game.");
        }

        Rental rental = new Rental(userId, gameId, LocalDate.now());
        return rentalRepository.save(rental);
    }

    public Rental returnGame(String userId, String gameId) {
        Rental rental = rentalRepository.findByUserIdAndGameIdAndReturnDateIsNull(userId, gameId)
                .orElseThrow(() -> new EntityNotFoundException("No active rental found for this game and user."));

        rental.setReturnDate(LocalDate.now());
        return rentalRepository.save(rental);
    }
}
