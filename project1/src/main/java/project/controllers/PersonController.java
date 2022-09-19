package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.dao.BookDAO;
import project.dao.PersonDAO;
import project.models.Person;
import project.util.PersonEditValidator;
import project.util.PersonNameValidator;

import javax.validation.Valid;

@RequestMapping("/people")
@Controller
public class PersonController {
    private final PersonDAO personDAO;
    private final BookDAO bookDAO;
    private final PersonNameValidator personNameValidator;
    private final PersonEditValidator personEditValidator;


    @Autowired
    public PersonController(PersonDAO personDAO, BookDAO bookDAO, PersonNameValidator personNameValidator, PersonEditValidator personEditValidator) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
        this.personNameValidator = personNameValidator;
        this.personEditValidator = personEditValidator;
    }

    @GetMapping
    public String index (Model model){
        model.addAttribute("people",personDAO.index());
        return "people/index";
    }


    @GetMapping("/{id}")
    public String show (@PathVariable("id") int id, Model model){
        model.addAttribute("person",personDAO.show(id));
        model.addAttribute("books",bookDAO.lockBookList(id));
        return "people/show";
    }
    @GetMapping("/addPerson")
    public String addPerson(Model model) {
        model.addAttribute("person",new Person());
        return "people/addPerson";
    }

    @PostMapping
    public String create(@ModelAttribute() @Valid Person person, BindingResult bindingResult){
        personNameValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/addPerson";
        }
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model,@PathVariable("id") int id) {
        model.addAttribute("person",personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        personEditValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors())
            return "people/edit";

        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }

}
