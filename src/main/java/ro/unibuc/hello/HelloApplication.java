package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import ro.unibuc.hello.data.PacientRepository;
import ro.unibuc.hello.data.DoctorEntity;
import ro.unibuc.hello.data.DoctorRepository;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.data.IntervalOrarEntity;
import ro.unibuc.hello.data.IntervalOrarRepository;
import ro.unibuc.hello.data.PacientEntity;
import ro.unibuc.hello.data.ProgramareEntity;
import ro.unibuc.hello.data.ProgramareRepository;

import java.time.LocalDate;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {InformationRepository.class, DoctorRepository.class, IntervalOrarRepository.class, ProgramareRepository.class, PacientRepository.class})
public class HelloApplication {
	@Autowired
	private InformationRepository informationRepository;
	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
    private ProgramareRepository programareRepository;
    @Autowired
    private IntervalOrarRepository intervalOrarRepository;
	@Autowired
	private PacientRepository pacientRepository;


	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		informationRepository.deleteAll();
		informationRepository.save(new InformationEntity("Overview modificat", "This is an example of using a data storage engine running separately from our applications server"));
	
		doctorRepository.deleteAll();
		DoctorEntity doctor1 = new DoctorEntity("Gigel Andrei", "cardiolog");
		doctorRepository.save(doctor1);
	
        intervalOrarRepository.deleteAll();
		IntervalOrarEntity intervalOrar1 = new IntervalOrarEntity(8, 0);
		intervalOrarRepository.save(intervalOrar1);
        IntervalOrarEntity intervalOrar2 = new IntervalOrarEntity(9, 0);
        intervalOrarRepository.save(intervalOrar2);

		pacientRepository.deleteAll();
		PacientEntity pacient1 = new PacientEntity("Bobita Stefan");
		pacientRepository.save(pacient1);

		programareRepository.deleteAll();
        programareRepository.save(new ProgramareEntity(intervalOrar1, doctor1, pacient1, LocalDate.now().plusDays(10)));
        programareRepository.save(new ProgramareEntity(intervalOrar2, doctor1, pacient1, LocalDate.now().plusDays(10)));	
	}
}
