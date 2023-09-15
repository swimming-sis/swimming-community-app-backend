package com.swimmingcommunityapp.like.entity;

import com.swimmingcommunityapp.BaseEntity;
import com.swimmingcommunityapp.post.entity.Post;
import com.swimmingcommunityapp.user.entity.User;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Likes")
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE likes SET deleted = true WHERE like_id = ?")
public class Like extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "deleted")
    private boolean deleted = false;

    public static Like createLike(User user, Post post) {
        return Like.builder()
                .user(user)
                .post(post)
                .build();
    }
}
