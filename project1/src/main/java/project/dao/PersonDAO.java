package project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import project.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    public Optional<Person> show(String fullName) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE fullName=?;",new BeanPropertyRowMapper<>(Person.class), new Object[]{fullName})
                .stream().findAny();
    }

    public List<Person> index() {
        return jdbcTemplate.query("select * from Person;",new BeanPropertyRowMapper<>(Person.class));
    }


    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?;",new BeanPropertyRowMapper<>(Person.class), new Object[]{id})
                .stream().findAny().orElse(null);
    }
    public void save(Person person) {
        jdbcTemplate.update("insert into Person(fullName,year) values (?,?);",person.getFullName(),person.getYear());
    }
    public void update(int id,Person updatePerson){
        jdbcTemplate.update("update  Person set fullName= ?,year = ? where id=?;",updatePerson.getFullName(),updatePerson.getYear(),id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from Person where id = ?;",id);
    }
}
