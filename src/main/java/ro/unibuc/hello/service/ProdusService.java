package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.Produs;
import ro.unibuc.hello.data.ProdusRepository;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.ProdusDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ProdusService {

    @Autowired
    private ProdusRepository produsRepository;

    private final AtomicLong counter = new AtomicLong();

    public Optional<ProdusDTO> getProdus(String id) {
      return produsRepository.findById(id);
    }

    public void createProdus(ProdusDTO produs) {
      produsRepository.save(produs);
    }

    public List<ProdusDTO> getAll() {
      return produsRepository.findAll();

    }

    public Produs toEntity(ProdusDTO produsDTO) {
      Produs produs = new Produs(produsDTO.getId(), produsDTO.getNume(), produsDTO.getPret());
      return produs;
    }

    public boolean updateProdus(ProdusDTO produsDTO) {
      ProdusDTO found = produsRepository.findById(produsDTO.getId()).orElse(null);
      if(found != null) {
        found.setNume(produsDTO.getNume());
        found.setPret(produsDTO.getPret());
        produsRepository.save(found);
        return true;
      }
      return false;
    }

    public boolean deleteProdus(String id) {
      ProdusDTO found = produsRepository.findById(id).orElse(null);
      if(found != null) {
        produsRepository.delete(found);
        return true;
      }
      return false;
    }
}