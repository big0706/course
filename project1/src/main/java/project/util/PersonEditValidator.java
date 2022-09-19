package project.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import project.dao.PersonDAO;
import project.models.Person;

@Component
public class PersonEditValidator implements Validator {

    private PersonDAO personDAO;

    public PersonEditValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (personDAO.show(person.getFullName()).isPresent() & personDAO.show(person.getId()).getId()!=person.getId() ) {
            errors.rejectValue("fullName","","Этот читатель уже существует!");
        }

    }
}