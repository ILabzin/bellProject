package com.labzin.bellProject.service;

import com.labzin.bellProject.controller.dto.GetLoginResponse;
import com.labzin.bellProject.controller.dto.PostLoginRequest;
import com.labzin.bellProject.controller.dto.PostLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BellProjectService {
    public GetLoginResponse getLogin() {

        waitResponce();

        return GetLoginResponse.builder()
                .login("Login1")
                .status("ok")
                .build();
    }

    public PostLoginResponse postLogin(PostLoginRequest request) {

        waitResponce();

        return PostLoginResponse.builder()
                .login(request.getLogin())
                .password(request.getPassword())
                .date(LocalDateTime.now())
                .build();
    }

    private void waitResponce() {
        try {
            Thread.sleep(1000 + (int)(Math.random() * 1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
