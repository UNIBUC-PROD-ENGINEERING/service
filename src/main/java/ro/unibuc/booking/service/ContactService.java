package ro.unibuc.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.booking.exception.EntityNotFoundException;
import ro.unibuc.booking.data.ContactInfoEntity;
import ro.unibuc.booking.data.ContactInfoRepository;
import ro.unibuc.booking.dto.ContactInfoDTO;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ContactService {
    
    @Autowired
    private ContactInfoRepository contactInfoRepository;

    public List<ContactInfoDTO> getContactInfo() {
        List<ContactInfoEntity> entities = contactInfoRepository.findAll();
        return entities.stream()
                .map(entity -> new ContactInfoDTO(entity.getPhoneNumber(), entity.getEmail(), entity.getAddress(), entity.getInstagram(), entity.getFacebook(), entity.getYoutube(), entity.getTiktok()))
                .collect(Collectors.toList());
    }

    // public ContactInfoDTO updateContactInfo(ContactInfoDTO contactInfoDTO) {
    //    List< ContactInfoEntity> entity = contactInfoRepository.findById(contactInfoDTO.getId())
    //             .orElseThrow(() -> new EntityNotFoundException("Contact info not found"));
    //     entity.setPhoneNumber(contactInfoDTO.getPhoneNumber());
    //     entity.setEmail(contactInfoDTO.getEmail());
    //     entity.setAddress(contactInfoDTO.getAddress());
    //     entity.setInstagram(contactInfoDTO.getInstagram());
    //     entity.setFacebook(contactInfoDTO.getFacebook());
    //     entity.setYoutube(contactInfoDTO.getYoutube());
    //     entity.setTiktok(contactInfoDTO.getTiktok());
    //     contactInfoRepository.save(entity);
    // }

}
