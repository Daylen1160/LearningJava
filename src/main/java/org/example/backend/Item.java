package org.example.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity // Stores this in the database
@Data   // Generates getters/setters
public class Item {
    @Id @GeneratedValue
    private Long id;
    private String name;

    // Empty constructor needed for JPA
    public Item() {}
    public Item(String name) { this.name = name; }
}