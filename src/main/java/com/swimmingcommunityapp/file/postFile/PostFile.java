package com.swimmingcommunityapp.file.postFile;


import com.swimmingcommunityapp.BaseEntity;
import com.swimmingcommunityapp.post.entity.Post;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Where(clause = "deleted = false")
@Table(name = "post_file")
@SQLDelete(sql = "UPDATE post_file SET deleted = true WHERE  post_file_id = ?")
public class PostFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_file_id")
    private Long id;

    private String uploadFileName;
    private String storedFileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder.Default
    private boolean deleted = false;

    public static PostFile makePostFile(String uploadFileName, String storedFileUrl, Post post) {
        return PostFile.builder()
                .uploadFileName(uploadFileName)
                .storedFileUrl(storedFileUrl)
                .post(post)
                .build();
    }
}