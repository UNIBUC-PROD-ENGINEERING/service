package ro.unibuc.hello.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.ApplicationEntity;
import ro.unibuc.hello.data.ApplicationRepository;
import java.util.List;

@Component
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public List<ApplicationEntity> getApplications() {
        return applicationRepository.findAll();
    }
}
