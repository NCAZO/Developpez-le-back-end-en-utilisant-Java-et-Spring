package com.openclassrooms.chatop.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@Entity(name = "rentals")
@Table(name = "rentals")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surface")
    private Double surface;

    @Column(name = "picture")
    private String picture;

    @Column(name = "price")
    private Double price;

    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "owner_id")
    private Long owner_id;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date created_at;

    @CreationTimestamp
    @Column(name = "updated_at", updatable = true)
    private Date updated_at;


    public Rental(Long id, String name, Double surface, String picture, Double price, String description, Long owner_id, Date created_at, Date updated_at) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.picture = picture;
        this.price = price;
        this.description = description;
        this.owner_id = owner_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Rental() {
    }
}
