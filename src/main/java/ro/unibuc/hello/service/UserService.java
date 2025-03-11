package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.user.User;
import ro.unibuc.hello.data.user.UserDTO;
import ro.unibuc.hello.data.user.UserRepository;
import ro.unibuc.hello.data.loyalty.LoyaltyCardEntity;
import ro.unibuc.hello.data.loyalty.LoyaltyCardRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoyaltyCardRepository loyaltyCardRepository;
    
    @Autowired
    private LoyaltyCardService loyaltyCardService;

    public String saveUser(UserDTO userDTO)
    {
        User userToSave = new User(userDTO);
        userRepository.save(userToSave);
        return userToSave.getId();
    }

    public User getUserById(String id) throws Exception
    {
        return userRepository.findById(id).orElseThrow(() -> new Exception(HttpStatus.NOT_FOUND.toString()));
    }

    public void deleteUserById(String id) throws Exception
    {
         // Trebuie să ștergem cardurile de fidelitate asociate
         List<LoyaltyCardEntity> userCards = loyaltyCardRepository.findByUserId(id);
         for (LoyaltyCardEntity card : userCards) {
             loyaltyCardService.deleteCard(card.getId());
         }
        
        // Apoi ștergem utilizatorul
        userRepository.deleteById(id);
    }
    
    // Metode noi pentru gestionarea cardurilor de fidelitate
    public LoyaltyCardEntity issueCardToUser(String userId, LoyaltyCardEntity.CardType cardType) throws Exception {
        return loyaltyCardService.issueCard(userId, cardType);
    }
    
    public List<LoyaltyCardEntity> getUserCards(String userId) throws Exception {
        return loyaltyCardService.getCardsByUser(userId);
    }

}
