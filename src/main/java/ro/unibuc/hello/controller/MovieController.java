package ro.unibuc.hello.controller;

import java.util.ArrayList;
import java.util.List;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.MovieRepository;
import ro.unibuc.hello.data.MovieEntity;
import ro.unibuc.hello.dto.MovieDTO;

@RestController
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/movie/getAll")
    @ResponseBody
    public List<MovieDTO> getMovies(){
        ArrayList<MovieDTO>movieDTOs = new ArrayList<>();
        movieRepository.findAll().forEach(movieEntity -> movieDTOs.add(new MovieDTO(movieEntity)));
        return movieDTOs;
    }

    @GetMapping("/movie/get")
    @ResponseBody
    public MovieDTO getMovie(@RequestParam(name="id")String id){
        MovieEntity movie = movieRepository.findById(String.valueOf(new ObjectId(id))).orElse(null);
        if (movie!=null)
            return new MovieDTO(movie);
        else
            return null;
    }

    @PostMapping("/movie/insert")
    @ResponseBody
    public MovieEntity insertMovie(@RequestParam(name="title")String title,@RequestParam(name="director")String director,
                                   @RequestParam(name="writer")String writer,@RequestParam(name="year")Integer year,
                                   @RequestParam(name="duration")Integer duration)
    {
        return movieRepository.save(new MovieEntity(title,director,writer,year,duration));
    }

    @PutMapping("/movie/update")
    @ResponseBody
    public MovieDTO updateMovie(@RequestParam(name="id")String id,
                                @RequestParam(name="title",required = false)String title,
                                @RequestParam(name="director",required = false)String director,
                                @RequestParam(name="writer",required = false)String writer,
                                @RequestParam(name="year",required = false)Integer year,
                                @RequestParam(name="duration",required = false)Integer duration){
        MovieEntity movie=movieRepository.findById(String.valueOf(new ObjectId(id))).orElse(null);
        if(movie!=null)
        {
            if(title!=null)
                movie.setTitle(title);
            if(director!=null)
                movie.setDirector(director);
            if(writer!=null)
                movie.setWriter(writer);
            if(year!=null)
                movie.setYear(year);
            if(duration!=null)
                movie.setDuration(duration);
            return new MovieDTO(movieRepository.save(movie));
        }
        else return null;
    }

    @DeleteMapping("/movie/delete")
    @ResponseBody
    public void deleteReview(@RequestParam(name="id")String id){

        movieRepository.deleteById(String.valueOf(new ObjectId(id)));
    }
}
