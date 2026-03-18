package ro.unibuc.prodeng.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.unibuc.prodeng.exception.DuplicateMasinaException;
import ro.unibuc.prodeng.exception.EntityNotFoundException;
import ro.unibuc.prodeng.model.Masina;
import ro.unibuc.prodeng.model.MasinaStatus;
import ro.unibuc.prodeng.repository.MasinaRepository;
import ro.unibuc.prodeng.request.MasinaRequest;
import ro.unibuc.prodeng.request.UpdateMasinaStatusRequest;
import ro.unibuc.prodeng.response.MasinaResponse;
import java.time.Year;

@Service
public class MasinaService {

    @Autowired
    private MasinaRepository masinaRepository;

    public List<MasinaResponse> getAllMasini() {
        return masinaRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public MasinaResponse getMasinaById(String id) throws EntityNotFoundException {
        Masina masina = masinaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        return toResponse(masina);
    }

    public MasinaResponse createMasina(MasinaRequest request) {
        validateMasinaRequest(request, null);
        
        Masina masina = new Masina(
                null,
                request.getMarca(),
                request.getModel(),
                request.getAn(),
                request.getPret(),
                request.getKilometri(),
                request.getCombustibil(),
                request.getPutereCp(),
                MasinaStatus.DISPONIBIL,
                request.getOwnerEmail()
        );
        Masina saved = masinaRepository.save(masina);
        return toResponse(saved);
    }

    public MasinaResponse updateMasina(String id, MasinaRequest request) throws EntityNotFoundException {
        validateMasinaRequest(request, id);

        Masina masina = masinaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        
        masina.setMarca(request.getMarca());
        masina.setModel(request.getModel());
        masina.setAn(request.getAn());
        masina.setPret(request.getPret());
        masina.setKilometri(request.getKilometri());
        masina.setCombustibil(request.getCombustibil());
        masina.setPutereCp(request.getPutereCp());
        masina.setOwnerEmail(request.getOwnerEmail());

        Masina saved = masinaRepository.save(masina);
        return toResponse(saved);
    }

    public MasinaResponse aplicaDiscount(String id) throws EntityNotFoundException {
        Masina masina = masinaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        
        double pretNou = masina.getPret() * 0.9;
        masina.setPret(pretNou);
        
        Masina saved = masinaRepository.save(masina);
        return toResponse(saved);
    }

    public void updateStatus(String id, UpdateMasinaStatusRequest request) throws EntityNotFoundException {
        Masina masina = masinaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        masina.setStatus(request.getStatus());
        masinaRepository.save(masina);
    }

    public void deleteMasina(String id) throws EntityNotFoundException {
        Masina masina = masinaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        if (masina.getStatus() == MasinaStatus.RESERVAT) {
            throw new IllegalArgumentException("Nu puteti sterge o masina care este rezervata");
        }
        masinaRepository.deleteById(id);
    }

    private MasinaResponse toResponse(Masina masina) {
        List<Masina> competitors = masinaRepository.findByMarca(masina.getMarca());
        boolean isSuspectLowPrice = false;

        if (!competitors.isEmpty()) {
            double avgPrice = competitors.stream()
                .mapToDouble(Masina::getPret)
                .average().orElse(0.0);
            
            if (masina.getPret() < avgPrice * 0.5) {
                isSuspectLowPrice = true;
            }
        }

        return new MasinaResponse(
                masina.getId(),
                masina.getMarca(),
                masina.getModel(),
                masina.getAn(),
                masina.getPret(),
                masina.getKilometri(),
                masina.getCombustibil(),
                masina.getPutereCp(),
                masina.getStatus(),
                masina.getOwnerEmail(),
                isSuspectLowPrice
        );
    }

    private void validateMasinaRequest(MasinaRequest request, String id) {
        if (request.getPret() <= 0) {
            throw new IllegalArgumentException("Pretul trebuie sa fie strict mai mare decat 0");
        }

        int currentYear = Year.now().getValue();
        if (request.getAn() < 1900 || request.getAn() > currentYear + 1) {
            throw new IllegalArgumentException("Anul fabricatiei este invalid");
        }

        if (request.getAn() >= currentYear && request.getKilometri() > 1000) {
            throw new IllegalArgumentException("O masina din anul curent sau viitor nu poate avea peste 1000 km");
        }

        if (request.getMarca() == null || request.getMarca().trim().isEmpty()) {
            throw new IllegalArgumentException("Marca nu poate fi goala");
        }

        if (request.getModel() == null || request.getModel().trim().isEmpty()) {
            throw new IllegalArgumentException("Modelul nu poate fi gol");
        }

        boolean exists;
        if (id == null) {
            exists = masinaRepository.existsByMarcaAndModelAndAn(request.getMarca(), request.getModel(), request.getAn());
        } else {
            Masina existingMasina = masinaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
            if (existingMasina.getStatus() == MasinaStatus.VANDUT) {
                 throw new IllegalArgumentException("O masina vanduta nu mai poate fi modificata.");
            }
            exists = masinaRepository.existsByMarcaAndModelAndAnAndIdNot(request.getMarca(), request.getModel(), request.getAn(), id);
        }

        if (exists) {
            throw new DuplicateMasinaException("O masina cu aceeasi marca, model si an exista deja");
        }
    }
}
