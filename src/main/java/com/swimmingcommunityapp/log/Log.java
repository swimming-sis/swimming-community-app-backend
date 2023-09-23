package com.swimmingcommunityapp.log;

import com.swimmingcommunityapp.BaseEntity;
import com.swimmingcommunityapp.file.logFile.LogFile;
import com.swimmingcommunityapp.file.postFile.PostFile;
import com.swimmingcommunityapp.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    private String date;

    @Builder.Default
    private boolean deleted = false;

    @OneToMany(mappedBy = "log",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<LogFile> logFiles = new ArrayList<>();

    public void updateLog(LogRequest dto) {
        this.distance = dto.getDistance();
        this.time = dto.getTime();
        this.calorie = dto.getCalorie();
        this.contents = dto.getContents();
        this.date = dto.getDate();
    }

}


