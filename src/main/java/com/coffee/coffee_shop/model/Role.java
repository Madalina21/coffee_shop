package com.coffee.coffee_shop.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(nullable = false, length = 15)
    private String name;

    @JsonIgnore//ca sa evite bucla infinita
    @ManyToMany(mappedBy = "roles")//ca sa am legatura catre useri
    private Set<User> users;

    public Role(String name) {
        this.name = name;
    }

}
