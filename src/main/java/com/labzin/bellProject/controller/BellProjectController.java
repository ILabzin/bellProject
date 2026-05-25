package com.labzin.bellProject.controller;

import com.labzin.bellProject.controller.dto.GetLoginResponse;
import com.labzin.bellProject.controller.dto.PostLoginRequest;
import com.labzin.bellProject.controller.dto.PostLoginResponse;
import com.labzin.bellProject.service.BellProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BellProjectController {

private final BellProjectService bellProjectService;

    @GetMapping("/get-login")
    public ResponseEntity<GetLoginResponse> getLogin(String login) {
        GetLoginResponse response = bellProjectService.getLogin(login);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/post-login")
    public ResponseEntity<PostLoginResponse> postLogin(@Valid @RequestBody PostLoginRequest postLoginRequest) {
        PostLoginResponse postLoginResponse = bellProjectService.postLogin(postLoginRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(postLoginResponse);
    }
}
