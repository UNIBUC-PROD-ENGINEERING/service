package ro.unibuc.hello.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.Donation;
import ro.unibuc.hello.data.DonationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RestController
public class DonationController {
    @Autowired
    DonationRepository donationRepository;
    private Integer amount;

    @GetMapping("/donations")
    public ResponseEntity<List<Donation>> getAllDonations(@RequestParam(required = false) String sender) {
    try{
        List<Donation> donations = new ArrayList<Donation>();
        if (sender == null)
            donationRepository.findAll().forEach(donations::add);
        else
            donationRepository.findBySender(sender).forEach(donations::add);
        if (donations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(donations, HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
    @GetMapping("/sum")
    public ResponseEntity<Integer> getSum(@RequestParam(required = false) String sender) {
        try {
            List<Donation> donations = new ArrayList<Donation>();
            Integer sum = 0;
            if (sender == null)
            {donationRepository.findAll().forEach(donations::add);
            for (int i = 0; i < donations.size(); i++) {
                sum += donations.get(i).getAmount();
            }
        }
            else
            {donationRepository.findBySender(sender).forEach(donations::add);
            for(int i=0;i<donations.size();i++)
            {
                sum+=donations.get(i).getAmount();
            }}
            if (donations.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(sum, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/donations/{id}")
    public ResponseEntity<Donation> getDonationById(@PathVariable("id") String id) {
        Optional<Donation> donationData = donationRepository.findById(id);
        if (donationData.isPresent()) {
            return new ResponseEntity<>(donationData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @PostMapping("/donations")
    public ResponseEntity<Donation> createDonation(@RequestBody Donation donation) {
        try {
            Donation _donation = donationRepository.save(new Donation(donation.getSender(), donation.getAmount(), donation.isAnonymous()));
            return new ResponseEntity<>(_donation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @DeleteMapping("/donations/{id}")
    public ResponseEntity<HttpStatus> deleteDonation(@PathVariable("id") String id) {
        try {
            donationRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @DeleteMapping("/donations")
    public ResponseEntity<HttpStatus> deleteAllDonations() {
        try {
            donationRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/donations/published")
    public ResponseEntity<List<Donation>> findByAnonymity() {
        try {
            List<Donation> donations = donationRepository.findByAnonymity(true);
            if (donations.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(donations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
