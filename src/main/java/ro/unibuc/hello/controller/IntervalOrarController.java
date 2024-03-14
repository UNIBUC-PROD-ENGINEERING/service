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
import ro.unibuc.hello.service.IntervalOrarService;

@RestController
@RequestMapping("/intervalOrar")
public class IntervalOrarController {
    @Autowired
    private IntervalOrarService intervalOrarService;

    @PostMapping
    public IntervalOrarEntity createIntervalOrar(@RequestBody IntervalOrarEntity intervalOrar) {
        return intervalOrarService.createIntervalOrar(intervalOrar);
    }

    @GetMapping("/{id}")
    public IntervalOrarEntity getIntervalOrarById(@PathVariable String id) {
        return intervalOrarService.getIntervalOrarById(id);
    }

    @GetMapping
    public List<IntervalOrarEntity> getAllIntervaleOrare() {
        return intervalOrarService.getAllIntervaleOrare();
    }

    @PutMapping("/{id}")
    public IntervalOrarEntity updateIntervalOrar(@PathVariable String id, @RequestBody IntervalOrarEntity intervalOrar) {
        intervalOrar.setId(id);
        return intervalOrarService.updateIntervalOrar(intervalOrar);
    }

    @DeleteMapping("/{id}")
    public void deleteIntervalOrar(@PathVariable String id) {
        intervalOrarService.deleteIntervalOrar(id);
    }
}
