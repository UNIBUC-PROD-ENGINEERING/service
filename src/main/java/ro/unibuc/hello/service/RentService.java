package ro.unibuc.hello.service;

import ro.unibuc.hello.data.Game;
import ro.unibuc.hello.data.Rent;
import ro.unibuc.hello.data.RentRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RentService {

    private final RentRepository rentRepository;
    private final GameService gameService;

    @Autowired
    public RentService(RentRepository rentRepository, GameService gameService) {
        this.rentRepository = rentRepository;
        this.gameService = gameService;
    }

    public List<Rent> getAllRents() {
        return rentRepository.findAll();
    }

    public Rent getRentById(String id) {
        return rentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rent not found with id: " + id));
    }

    public List<Rent> getRentsByUserId(String userId) {
        return rentRepository.findByUserId(userId);
    }

    public List<Rent> getRentsByGameId(String gameId) {
        return rentRepository.findByGameId(gameId);
    }

    public Rent rentGame(String userId, String gameId, int rentDays) {
        // Verify that the game exists
        Game game = gameService.getGameById(gameId);

        // Check if the game is already rented by this user and not returned
        List<Rent> activeRents = rentRepository.findByUserIdAndGameIdAndIsReturnedFalse(userId, gameId);
        if (!activeRents.isEmpty()) {
            throw new IllegalStateException("This game is already rented by this user");
        }

        // Create new rent
        Rent rent = new Rent(userId, gameId, rentDays);
        rent.setRentDate(LocalDateTime.now());
        rent.setReturned(false);

        return rentRepository.save(rent);
    }

    public Rent returnGame(String userId, String gameId) {
        // Find active rent for this user and game
        List<Rent> activeRents = rentRepository.findByUserIdAndGameIdAndIsReturnedFalse(userId, gameId);

        if (activeRents.isEmpty()) {
            throw new EntityNotFoundException("No active rental found for this user and game");
        }

        // Get the most recent active rent (should be only one, but just in case)
        Rent rent = activeRents.get(0);

        // Set return information
        rent.setReturnDate(LocalDateTime.now());
        rent.setReturned(true);

        return rentRepository.save(rent);
    }
}