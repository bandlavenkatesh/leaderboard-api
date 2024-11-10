package com.venkatesh.leaderboard.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    
    @Id
    private String userId;
    private String username;
    private int score = 0;
    private Set<String> badges = new HashSet<>();
   
    public void setScore(int score){
        this.score = score;
        assignBadge();
    }

    private void assignBadge(){
        badges.clear();
        if(score >= 60){
            badges.add("Code Master");
        }else if(score >= 30){
            badges.add("Code Champ");
        }else if(score >= 1){
            badges.add("Code Ninja");
        }
    }
}


