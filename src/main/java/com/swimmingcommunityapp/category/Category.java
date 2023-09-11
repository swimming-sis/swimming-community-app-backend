package com.swimmingcommunityapp.category;

import com.swimmingcommunityapp.post.entity.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Post> posts = new ArrayList<>();

}