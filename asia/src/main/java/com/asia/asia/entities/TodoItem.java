package com.asia.asia.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Entity
@Table(name = "todo_items")
@JacksonXmlRootElement(localName = "TodoItems")
public class TodoItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TodoSequence")
    @JsonProperty("id")
    @JacksonXmlProperty(isAttribute = true)
    @JsonIgnore
    private Long id;

    @JsonProperty("description")
    @JacksonXmlProperty
    private String description;

    @JsonProperty("priority")
    @JacksonXmlProperty
    private Priority priority;

    @JsonProperty("isComplete")
    @JacksonXmlProperty
    private Boolean isComplete;

    @JsonProperty("createdAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    @JsonProperty("tillWhen")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime tillWhen;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return String.format("TodoItems{id=%d, description='%s', isComplete='%s',createdAt='%s', updatedAt='%s', tillWhen='%s'} ",
                id, description, isComplete, createdAt, updatedAt, tillWhen);
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @JsonIgnore
    public String getFormattedCreatedAt() {
        return createdAt != null ? createdAt.format(formatter) : null;
    }

    @JsonIgnore
    public String getFormattedUpdatedAt() {
        return updatedAt != null ? updatedAt.format(formatter) : null;
    }

    @JsonIgnore
    public String getFormattedTillWhen() {
        return tillWhen != null ? tillWhen.format(formatter) : null;
    }
}
