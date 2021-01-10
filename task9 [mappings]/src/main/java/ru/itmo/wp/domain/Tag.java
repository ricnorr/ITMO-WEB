package ru.itmo.wp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"name"})})
public class Tag implements Comparable<Tag> {
    @GeneratedValue
    @Id
    Long id;

    @NotNull
    String name;

    public Tag(String value) {
        this.name = value;
    }

    public Tag() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Tag tag) {
        return (this.getName().compareTo(tag.getName()));
    }
}
