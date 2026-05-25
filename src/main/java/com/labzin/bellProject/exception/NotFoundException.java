package com.labzin.bellProject.exception;
public class NotFoundException extends RuntimeException{
    public NotFoundException(String login) {
        super("Позователь не найден: " + login);
    }
}

