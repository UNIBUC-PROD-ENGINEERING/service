package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.VipLoungeEntity;
import ro.unibuc.hello.data.VipLoungeRepository;
import ro.unibuc.hello.dto.VipLounge;
import ro.unibuc.hello.exception.EntityNotFoundException;

@Component
public class VipLoungeService {

    @Autowired
    private VipLoungeRepository vipLoungeRepository;

    public VipLounge getVipLoungeByEntryPrice(String entryPrice) throws EntityNotFoundException {
        VipLoungeEntity entity = vipLoungeRepository.findByEntryPrice(entryPrice);
        if(entity == null) {
            throw new EntityNotFoundException(entryPrice);
        }
        return new VipLounge(entity.entryPrice, entity.location);
    }
}
