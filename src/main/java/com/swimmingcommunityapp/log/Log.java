package com.swimmingcommunityapp.log;

import com.swimmingcommunityapp.BaseEntity;
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
@SQLDelete(sql = "UPDATE log SET deleted = true WHERE log_id = ?")
@Entity
@Table(name = "log")
public class Log extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Long distance;
    private Long time;
    private Long calorie;

    private String contents;

}


