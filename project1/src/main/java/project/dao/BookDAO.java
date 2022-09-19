package project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import project.mapper.BookMapper;
import project.models.Book;


import java.util.List;
import java.util.Optional;


@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public Optional<Book> show(String name) {
        return jdbcTemplate.query("SELECT * FROM books WHERE name=?;",new BookMapper(), new Object[]{name})
                .stream().findAny();
    }

    public List<Book> index() {
        return jdbcTemplate.query("select * from books;",new BookMapper());
    }

    public Book showBook(int id) {
        return jdbcTemplate.query("SELECT * FROM books WHERE id=?;",new BookMapper(), new Object[]{id})
                .stream().findAny().orElse(null);
    }
    public void save(Book book) {
        jdbcTemplate.update("insert into books(name,author,year) values (?,?,?);",book.getName(),book.getAuthor(),book.getYear());
    }
    public void update(int id,Book book){
        jdbcTemplate.update("update  Books set name = ?, author = ? ,year = ? where id=?;",book.getName(),book.getAuthor(),book.getYear(),id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from Books where id = ? ;",id);
    }

    public void lockBook(int personId, int bookId) {
        jdbcTemplate.update("update books set person_id = ?  where id =?;",personId,bookId);
    }
    public void unlockBook(int bookId){
        jdbcTemplate.update("update books set person_id = null  where id =?;",bookId);
    }
    public List<Book> lockBookList(int id) {
        return jdbcTemplate.query("select * from books where person_id=?;",new BookMapper(),new Object[]{id});
    }

}

