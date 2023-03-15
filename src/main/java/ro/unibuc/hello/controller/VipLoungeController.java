package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.dto.VipLounge;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.VipLoungeService;

@Controller
public class VipLoungeController {

    @Autowired
    public VipLoungeService vipLoungeService;

    @GetMapping("/getVipLoungeByEntryPrice")
    @ResponseBody
    public VipLounge getVipLoungeByEntryPrice(@RequestParam(name="entryPrice", required = true) String entryPrice) throws EntityNotFoundException {
        return vipLoungeService.getVipLoungeByEntryPrice(entryPrice);
    }
}
