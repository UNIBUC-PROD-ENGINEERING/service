package ro.unibuc.hello.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.unibuc.hello.data.IntervalOrarEntity;
import ro.unibuc.hello.data.IntervalOrarRepository;

@RestController
@RequestMapping("/intervalorar")
public class IntervalOrarController {

    @Autowired
    private IntervalOrarRepository intervalOrarRepository;

    @PostMapping
    public IntervalOrarEntity createIntervalOrar(@RequestBody IntervalOrarEntity intervalOrar) {
        return intervalOrarRepository.save(intervalOrar);
    }

    @GetMapping("/{id}")
    public IntervalOrarEntity getIntervalOrarById(@PathVariable String id) {
        return intervalOrarRepository.findById(id).orElse(null);
    }

    @GetMapping
    public List<IntervalOrarEntity> getAllIntervalOrars() {
        return intervalOrarRepository.findAll();
    }

    @PutMapping("/{id}")
    public IntervalOrarEntity updateIntervalOrar(@PathVariable String id, @RequestBody IntervalOrarEntity intervalOrar) {
        intervalOrar.setId(id);
        return intervalOrarRepository.save(intervalOrar);
    }

    @DeleteMapping("/{id}")
    public void deleteIntervalOrar(@PathVariable String id) {
        intervalOrarRepository.deleteById(id);
    }
}
