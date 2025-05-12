package dev.trela.repository;

import dev.trela.model.Author;
import dev.trela.service.MessageService;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@Transactional
public class AuthorRepository {


    private final SessionFactory sessionFactory;
    private final MessageService messageService;

    public AuthorRepository(SessionFactory sessionFactory,MessageService messageService){
        this.messageService = messageService;
        this.sessionFactory = sessionFactory;
    }


    public Optional<Author> findByName(String authorName) {
        Session session = sessionFactory.getCurrentSession();
        try {
            Author author = session.createQuery(
                            "FROM Author WHERE LOWER(name) = LOWER(:name)", Author.class)
                    .setParameter("name", authorName)
                    .getSingleResult();
            return Optional.of(author);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public void save(Author author){
       Session session = sessionFactory.getCurrentSession();
       session.persist(author);
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().refresh(author);
    }


//    //tested works
//    public void update(Author author){
//        String sql = """
//        UPDATE authors
//        SET name = ?
//        WHERE author_id = ?
//    """;
//        int updatedRows = jdbcTemplate.update(sql,
//                author.getName(),
//                author.getId());
//        if (updatedRows == 0){
//            throw new NoSuchElementException("Author with id " + author.getId() + " not found");
//        }
//    }
//
//    //tested works
//    public List<Author> findAll(){
//        String sql = """
//                SELECT a.author_id, a.name FROM authors a
//                ORDER BY a.author_id
//                """;
//        return jdbcTemplate.query(sql,(rs,rowNum) -> {
//            Author author = new Author();
//            author.setId(rs.getInt("author_id"));
//            author.setName(rs.getString("name"));
//            return author;
//        });
//    }
//
//    //tested works
//    public void deleteById(int id){
//        String sql = "DELETE FROM authors WHERE author_id = ? ";
//        int deletedRows = jdbcTemplate.update(sql,id);
//        if(deletedRows == 0){
//            throw new NoSuchElementException("Author with id " + id + " not found");
//        }
//    }



}
