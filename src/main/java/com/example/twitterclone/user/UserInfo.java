package com.example.twitterclone.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

@JsonSerialize
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
class UserInfo {
    private String name;
    private String password;
}
