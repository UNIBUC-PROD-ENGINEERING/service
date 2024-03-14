package ro.unibuc.hello.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.unibuc.hello.data.IntervalOrarEntity;
import ro.unibuc.hello.data.IntervalOrarRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;

@Service
public class IntervalOrarService {
    @Autowired
    private IntervalOrarRepository intervalOrarRepository;

    public IntervalOrarEntity createIntervalOrar(IntervalOrarEntity intervalOrar) {
        intervalOrar.setId(null);
        intervalOrarRepository.save(intervalOrar);
        return intervalOrar;
    }

    public IntervalOrarEntity getIntervalOrarById(String id) {
        return intervalOrarRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("intervalOrar"));
    }

    public List<IntervalOrarEntity> getAllIntervaleOrare() {
        return intervalOrarRepository.findAll();
    }

    public IntervalOrarEntity updateIntervalOrar(IntervalOrarEntity intervalOrar) {
        intervalOrarRepository.findById(intervalOrar.getId()).orElseThrow(() -> new EntityNotFoundException("intervalOrar"));
        intervalOrarRepository.save(intervalOrar);
        return intervalOrar;
    }

    public void deleteIntervalOrar(String id) {
        IntervalOrarEntity intervalOrar = intervalOrarRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("intervalOrar"));
        intervalOrarRepository.delete(intervalOrar);
    }
}
