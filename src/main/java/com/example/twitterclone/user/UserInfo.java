package com.example.twitterclone.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

@JsonSerialize
@Setter
@Getter
class UserInfo {
    private String name;
    private String password;
}
