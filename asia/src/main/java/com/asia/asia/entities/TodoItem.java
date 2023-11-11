package com.asia.asia.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

// TODO: Dodać relacje do Usera (Pewnie one to Many )
@Getter
@Setter
@Entity
@Table(name = "todo_items")
public class TodoItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;

    private Boolean isComplete;

    private Instant createdAt;

    private Instant updatedAt;

    private Date tillWhen;

    @Override
    public String toString(){
        return String.format("TodoItems{id=%d, description='%s', isComplete='%s',createdAt='%s', updatedAt='%s', tillWhen='%s'} ",
                id, description, isComplete, createdAt,updatedAt, tillWhen);
    }
}
