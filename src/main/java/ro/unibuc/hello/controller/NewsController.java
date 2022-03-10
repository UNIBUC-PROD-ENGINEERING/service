package ro.unibuc.hello.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.News;
import ro.unibuc.hello.data.NewsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Controller that provides APIs for creating, retrieving, updating, deleting and finding News.


@RestController // is used to define a controller and to indicate that the return value of the methods should be bound to the web response body.
public class NewsController {
    @Autowired
    NewsRepository newsRepository;
    @GetMapping("/news")
    // ResponseEntity represents the whole HTTP response: status code, headers, and body.

    public ResponseEntity<List<News>> getAllNews(@RequestParam(required = false) String title) {
        try {
            List<News> news = new ArrayList<News>();
            if (title == null)
                newsRepository.findAll().forEach(news::add);
            else
                newsRepository.findByTitleContaining(title).forEach(news::add);
            if (news.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(news, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/news/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable("id") String id) {
        Optional<News> newsData = newsRepository.findById(id);
        if (newsData.isPresent()) {
            return new ResponseEntity<>(newsData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/news")
    public ResponseEntity<News> createNews(@RequestBody News news) {
        try {
            System.out.println(news);
            News _news = newsRepository.save(new News(news.getTitle(), news.getDescription(), news.isPublished()));
            return new ResponseEntity<>(_news, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/news/{id}")
    public ResponseEntity<News> updateNews(@PathVariable("id") String id, @RequestBody News news) {
        Optional<News> newsData = newsRepository.findById(id);
        if (newsData.isPresent()) {
            News _news = newsData.get();
            _news.setTitle(news.getTitle());
            _news.setDescription(news.getDescription());
            _news.setPublished(news.isPublished());
            return new ResponseEntity<>(newsRepository.save(_news), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/news/{id}")
    public ResponseEntity<HttpStatus> deleteNews(@PathVariable("id") String id) {
        try {
            newsRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/news")
    public ResponseEntity<HttpStatus> deleteAllNews() {
        try {
            newsRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/news/published")
    public ResponseEntity<List<News>> findByPublished() {
        try {
            List<News> news = newsRepository.findByPublished(true);
            if (news.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(news, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}