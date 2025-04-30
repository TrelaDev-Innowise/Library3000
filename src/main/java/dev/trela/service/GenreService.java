package dev.trela.service;
import dev.trela.model.Genre;
import dev.trela.repository.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final MessageService messageService;

    public GenreService(GenreRepository genreRepository,MessageService messageService){
        this.genreRepository = genreRepository;
        this.messageService = messageService;
    }

    public void checkIfGenreExists(String genreName) {
        genreRepository.findByGenreName(genreName).orElseThrow(() ->
                new NoSuchElementException(messageService.getMessage("error.genre.notfound")));
    }


    public List<Genre> getAllGenres(){
        return genreRepository.findAll();
    }

    @Transactional
    public void updateGenre(Genre genre) throws NoSuchElementException{
        genreRepository.update(genre);
    }

    @Transactional
    public void deleteGenre(int id) throws NoSuchElementException{
        genreRepository.deleteById(id);
    }





}
