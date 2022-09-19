package project.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {
    @Size(min = 2,max = 30,message = "Допустимое значение от 2 до 30 символов")
    @NotEmpty(message = "Название не может быть пустым!")
    private String name;
    @Size(min = 2,max = 30,message = "Допустимое значение от 2 до 30 символов")
    @NotEmpty(message = "Автор не может быть пустым!")
    private String author;
    @Min(value = 0, message = "Настолько древних книг не держим!")
    private int year;
    private int id;
    private int person_id;

    public Book(int id) {
        this.id = id;
    }

    public Book() {
    }

    public Book(String name, String author, int year, int id, int person_id) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.id = id;
        this.person_id = person_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int age) {
        this.year = age;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }
}
