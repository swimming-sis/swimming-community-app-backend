package com.swimmingcommunityapp.swimmingPool;

import com.swimmingcommunityapp.review.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE swimming_pool SET deleted = true WHERE swimming_pool_id = ?")
public class SwimmingPool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "swimming_pool_id")
    private Long id;

    private Long uniqueNumber;
    private String placeName;
    private String placeUrl;
    private String roadAddressName;
    private String phone;

    @Builder.Default
    private boolean deleted = false;

    @OneToMany(mappedBy = "swimmingPool",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

}
