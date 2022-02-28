package ro.unibuc.link.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.link.data.UrlEntity;
import ro.unibuc.link.dto.UrlDeleteDTO;
import ro.unibuc.link.dto.UrlShowDTO;
import ro.unibuc.link.services.UrlService;


@Controller
@RequestMapping("/short-link")
public class UrlController {
    @Autowired
    private UrlService urlService;

    @GetMapping("/check/{url}")
    public @ResponseBody
    boolean checkIfUrlIsAvailable(@PathVariable String url) {
        return urlService.checkInternalUrlIsAvailable(url);
    }

    @PostMapping("/set")
    public @ResponseBody
    UrlShowDTO setMapping(@RequestBody UrlEntity urlEntity) {
        return urlService.setRedirectMapping(urlEntity);
    }

    @DeleteMapping("")
    public @ResponseBody
    UrlShowDTO deleteMapping(@RequestBody UrlDeleteDTO urlDeleteDTO) {
        return urlService.deleteRedirectMapping(urlDeleteDTO.getInternalUrl(), urlDeleteDTO.getDeleteWord());
    }

    @GetMapping("/redirect/{url}")
    public String redirect(@PathVariable String url) {
        return urlService.getRedirectMapping(url);
    }
}
