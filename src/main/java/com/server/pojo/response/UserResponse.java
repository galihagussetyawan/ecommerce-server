package com.server.pojo.response;

import com.server.domain.DetailContact;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String username;
    private String name;
    private Object roles;
    private DetailContact userDetail;
}