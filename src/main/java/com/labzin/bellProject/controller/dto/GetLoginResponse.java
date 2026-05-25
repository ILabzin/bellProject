package com.labzin.bellProject.controller.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetLoginResponse {

    private String login;
    private String status;
    private String email;
    private String registration;
}
