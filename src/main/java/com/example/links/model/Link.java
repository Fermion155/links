package com.example.links.model;

import com.example.links.tools.generators.CustomSequence;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Link {

    @CustomSequence(name = "mysequence")
    @Id
    @Column(nullable = true)
    private String id;

    @Column(nullable = false, unique = true)
    private String url;
}
