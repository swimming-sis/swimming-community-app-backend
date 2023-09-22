package com.swimmingcommunityapp.post.entity;

import com.swimmingcommunityapp.BaseEntity;
import com.swimmingcommunityapp.category.Category;
import com.swimmingcommunityapp.comment.entity.Comment;
import com.swimmingcommunityapp.file.postFile.PostFile;
import com.swimmingcommunityapp.like.entity.Like;
import com.swimmingcommunityapp.user.entity.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor
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
    private Long likeCnt= 0L;

    @Builder.Default
    private Long commentCnt= 0L;

    @Builder.Default
    private boolean deleted = false;

    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Like> like = new ArrayList<>();

    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PostFile> postFiles = new ArrayList<>();

    public void updatePost(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public void updateLike(Long likeCnt,Long cnt) {
        this.likeCnt = likeCnt + cnt;
    }public void updateCommentCnt(Long commentCnt,Long cnt) {
        this.commentCnt = commentCnt + cnt;
    }

}
