package com.swimmingcommunityapp.review;


import com.swimmingcommunityapp.BaseEntity;
import com.swimmingcommunityapp.swimmingPool.SwimmingPool;
import com.swimmingcommunityapp.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE review SET deleted = true WHERE review_id = ?")
@Entity
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "swimming_pool_id")
    private SwimmingPool swimmingPool;

    private Long ratingStar;

    private boolean deleted = false;

    public void updateReview(String contents,Long ratingStar){
        this.contents = contents;
        this.ratingStar = ratingStar;
    }
}
