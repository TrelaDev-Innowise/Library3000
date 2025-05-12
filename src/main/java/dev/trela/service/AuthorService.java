package dev.trela.service;

import dev.trela.model.Author;

import dev.trela.repository.AuthorRepository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;
    @Autowired
    private SessionFactory sessionFactory;

    public AuthorService(AuthorRepository authorRepository, SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
        this.authorRepository = authorRepository;
    }


    public Optional<Author> findAuthorByName(String authorName){
        return authorRepository.findByName(authorName);
    }


    public Author findOrCreateAuthor(String authorName)
    {
        Session session = sessionFactory.getCurrentSession();
        Optional<Author> optionalAuthor = findAuthorByName(authorName);
        if(optionalAuthor.isPresent()){
            return session.merge(optionalAuthor.get());
        }
        Author newAuthor = new Author(authorName);
        session.persist(newAuthor);
        return newAuthor;

    }




//
//    public List<Author> getAllAuthors(){
//        return authorRepository.findAll();
//    }
//
//    public void updateAuthor(Author author) throws NoSuchElementException{
//        authorRepository.update(author);
//    }
//
//    public void deleteAuthor(int id) throws NoSuchElementException{
//        authorRepository.deleteById(id);
//    }


}
