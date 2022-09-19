package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.dao.BookDAO;
import project.dao.PersonDAO;
import project.models.Book;
import project.models.Person;
import project.util.BookEditValidator;
import project.util.BookNameValidator;

import javax.validation.Valid;


@Controller
@RequestMapping("/books")
public class BookController {

    private final PersonDAO personDAO;
    private final BookDAO bookDAO;
    private final BookNameValidator bookNameValidator;
    private final BookEditValidator bookEditValidator;

    @Autowired
    public BookController(PersonDAO personDAO, BookDAO bookDAO, BookNameValidator bookNameValidator, BookEditValidator bookEditValidator) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
        this.bookNameValidator = bookNameValidator;
        this.bookEditValidator = bookEditValidator;
    }

    @GetMapping
    public String index (Model model){
        model.addAttribute("books",bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String showBook (@PathVariable("id") int id, Model model, @ModelAttribute("person")Person person){
        model.addAttribute("book",bookDAO.showBook(id));
        model.addAttribute("people",personDAO.index());
        model.addAttribute("people1",personDAO.show(bookDAO.showBook(id).getPerson_id()));
        return "books/show";
    }
    @GetMapping("/add_book")
    public String addNewBook(Model model) {
        model.addAttribute("book",new Book());
        return "books/add_book";
    }
    @PostMapping
    public String createBook(@ModelAttribute() @Valid Book book, BindingResult bindingResult){
        bookNameValidator.validate(book,bindingResult);
        if (bindingResult.hasErrors()) {
            return "books/add_book";
        }
        bookDAO.save(book);

        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String editBook(Model model,@PathVariable("id") int id) {
        model.addAttribute("book",bookDAO.showBook(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        bookEditValidator.validate(book,bindingResult);
        if (bindingResult.hasErrors())
            return "books/edit";

        bookDAO.update(id,book);
        return "redirect:/books";
    }

    @DeleteMapping("{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }
}
