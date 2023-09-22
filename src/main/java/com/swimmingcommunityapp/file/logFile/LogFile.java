package com.swimmingcommunityapp.file.logFile;


import com.swimmingcommunityapp.file.postFile.PostFile;
import com.swimmingcommunityapp.log.Log;
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
@Table(name = "log_file")
@SQLDelete(sql = "UPDATE log_file SET deleted = true WHERE  log_file_id = ?")
public class LogFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_file_id")
    private Long id;

    private String uploadFileName;
    private String storedFileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "log_id")
    private Log log;

    @Builder.Default
    private boolean deleted = false;

    public static LogFile makeLogFile(String uploadFileName, String storedFileUrl, Log log) {
        return LogFile.builder()
                .uploadFileName(uploadFileName)
                .storedFileUrl(storedFileUrl)
                .log(log)
                .build();
    }

}
