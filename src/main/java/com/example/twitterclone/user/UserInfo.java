package com.example.twitterclone.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@JsonSerialize
@Data
class UserInfo {
    private String name;
    private String password;
}
