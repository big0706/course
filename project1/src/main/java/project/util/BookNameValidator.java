package project.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import project.dao.BookDAO;
import project.models.Book;

@Component
public class BookNameValidator implements Validator {
    private BookDAO bookDAO;

    @Autowired
    public BookNameValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if (bookDAO.show(book.getName()).isPresent() ) {
            errors.rejectValue("name","","Такая книга существует в списке!");
        }
    }
}
