package com.example.thithpart2.Model;
import java.sql.Date;

public class Book {
    private Long id;

    private String title;

    private Date publishDate;

    private String description;

    private Float price;

    private Author author;

    private Category category;

    private EType type;

    public Book() {
    }

    public Book(Long id, String title, Date publishDate, String description, Float price, Author author, Category category, EType etype) {
        this.id = id;
        this.title = title;
        this.publishDate = publishDate;
        this.description = description;
        this.price = price;
        this.author = author;
        this.category = category;
        this.type = etype;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public EType getType() {
        return type;
    }

    public void setType(EType type) {
        this.type = type;
    }
}
