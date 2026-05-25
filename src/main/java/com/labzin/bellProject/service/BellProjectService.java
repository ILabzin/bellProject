package com.labzin.bellProject.service;

import com.labzin.bellProject.controller.dto.GetLoginResponse;
import com.labzin.bellProject.controller.dto.PostLoginRequest;
import com.labzin.bellProject.controller.dto.PostLoginResponse;
import com.labzin.bellProject.db.DataBaseWorker;
import com.labzin.bellProject.exception.NotFoundException;
import com.labzin.bellProject.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BellProjectService {

    private final DataBaseWorker dataBaseWorker;

    public GetLoginResponse getLogin(String login) {

        log.info("Запрос получения данных пользователя: {}", login);
        waitResponse();

        return Optional.ofNullable(dataBaseWorker.getUser(login))
                .map(user -> GetLoginResponse.builder()
                        .login(user.getLogin())
                        .status("ok")
                        .email(user.getEmail())
                        .build())
                .orElseThrow(() -> new NotFoundException(login)); // Спросить почему в задаче 500, а не 404?
    }

    public PostLoginResponse postLogin(PostLoginRequest request) {

        log.info("Создание нового пользователя: {}", request.getLogin());
        waitResponse();

        Optional.ofNullable(dataBaseWorker.getUser(request.getLogin()))
                .ifPresent(user -> {
                    throw new RuntimeException("пользователь уже существует");
                });

        User newUser = User.builder()
                .login(request.getLogin())
                .password(request.getPassword())
                .email(request.getEmail())
                .date(Date.valueOf(LocalDate.now()))
                .build();

        return PostLoginResponse.builder()
                .rowsAffected(dataBaseWorker.insertUser(newUser))
                .build();
    }

    private void waitResponse() {
        try {
            Thread.sleep(1000 + (int)(Math.random() * 1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
