package ro.unibuc.hello.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import ro.unibuc.hello.data.News;
import ro.unibuc.hello.data.NewsRepository;

import java.util.ArrayList;
import java.util.List;

public class NewsService {
    @Autowired
    NewsRepository newsRepository;

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
}
