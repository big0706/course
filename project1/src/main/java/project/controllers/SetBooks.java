package project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.dao.BookDAO;

import project.models.Person;

@Controller
@RequestMapping("/set")
public class SetBooks {


    private final BookDAO bookDAO;

    public SetBooks( BookDAO bookDAO) {

        this.bookDAO = bookDAO;
    }


    @PatchMapping("/{id}")
    public String setBook(@PathVariable("id") int id, @ModelAttribute("person")Person person) {
        bookDAO.lockBook(person.getId(),id);
        return "redirect:/books/"+id;
    }

    @PatchMapping("/unlock/{id}")
    public String unlockBook(@PathVariable("id") int id){
        bookDAO.unlockBook(id);
        return "redirect:/books/"+id;
    }

}
