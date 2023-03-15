package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.models.PolicyRepository;
import ro.unibuc.hello.models.Policy;

@Controller // This means that this class is a Controller
@RequestMapping(path="/")
public class MainController {

    @Autowired
    private PolicyRepository policyRepository;

    @PostMapping(path="/addPolicy") // Map ONLY POST Requests
    public @ResponseBody String addNewUser (
            @RequestParam String policyNumber,
            @RequestParam String policyHolderFirstName,
            @RequestParam String policyHolderLastName,
            @RequestParam String CNP) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Policy p = new Policy();
        p.setCNP(CNP);
        p.setPolicyNumber(policyNumber);
        p.setPolicyHolderFirstName(policyHolderFirstName);
        p.setPolicyHolderLastName(policyHolderLastName);
        policyRepository.save(p);
        return "Saved";
    }

    @GetMapping(path="/getPolicy")
    public @ResponseBody Iterable<Policy> getAllPolicy() {
        // This returns a JSON or XML with the users
        return policyRepository.findAll();
    }


}
