package com.swimmingcommunityapp.post.entity;

import com.swimmingcommunityapp.BaseEntity;
import com.swimmingcommunityapp.category.Category;
import com.swimmingcommunityapp.user.entity.User;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE post SET deleted = true WHERE post_id = ?")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @Builder.Default
    private boolean deleted = false;


    public void updatePost(String title, String body) {
        this.title = title;
        this.body = body;
    }


}
