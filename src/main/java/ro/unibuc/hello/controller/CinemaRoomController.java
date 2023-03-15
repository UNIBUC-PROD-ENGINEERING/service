package ro.unibuc.hello.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.data.LocationRepository;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.Location;
import ro.unibuc.hello.dto.CinemaRoom;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.HelloWorldService;
import ro.unibuc.hello.service.LocationService;
import ro.unibuc.hello.service.CinemaRoomService;
@Controller
public class CinemaRoomController {

    @Autowired
    public CinemaRoomService cinemaRoomService;

    @GetMapping("/getCinemaRoomByNumber")
    @ResponseBody
    public CinemaRoom getCinemaRoomByNumber(@RequestParam(name="number", required = true) String number) throws EntityNotFoundException{
        return cinemaRoomService.getCinemaRoomByNumber(number);
    }
}
