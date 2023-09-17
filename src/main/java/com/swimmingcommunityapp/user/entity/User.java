package com.swimmingcommunityapp.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String userName;
    private String nickName;
    private String phoneNumber;
    private String password;


    public void updateUser(String nickName,String phoneNumber,String password) {
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

}
