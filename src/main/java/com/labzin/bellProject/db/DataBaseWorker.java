package com.labzin.bellProject.db;

import com.labzin.bellProject.config.DataSourceProperties;
import com.labzin.bellProject.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataBaseWorker {

    private final DataSourceProperties dataSourceProperties;

    public User getUser(String login) {
        log.info("Поиск пользователя по логину: {}", login);

        String sql = "SELECT a.login, a.password, a.date, u.email FROM auth a " +
                "JOIN user_data u ON a.login = u.login WHERE a.login = ?";

        try (Connection connection = DriverManager.getConnection(
                dataSourceProperties.getUrl(),
                dataSourceProperties.getUsername(),
                dataSourceProperties.getPassword());
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, login);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    User user = User.builder()
                            .login(resultSet.getString("login"))
                            .password(resultSet.getString("password"))
                            .email(resultSet.getString("email"))
                            .date(resultSet.getDate("date"))
                            .build();
                    log.debug("Найден пользователь: {}", user.getLogin());
                    return user;
                } else {
                    log.warn("Пользователь с логином {} не найден", login);
                    return null;
                }
            }

        } catch (SQLException e) {
            log.error("Ошибка при получении пользователя с логином: {}", login, e);
            return null;
        }
    }

    public int insertUser(User user) {
        log.info("Вставка нового пользователя: {}", user.getLogin());

        String sql = """
        INSERT INTO auth (login, password, date) VALUES (?, ?, ?);\n
        INSERT INTO user_data (login, email) VALUES (?, ?);
        """;

        try (Connection connection = DriverManager.getConnection(
                dataSourceProperties.getUrl(),
                dataSourceProperties.getUsername(),
                dataSourceProperties.getPassword());
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setDate(3, user.getDate());
            ps.setString(4, user.getLogin());
            ps.setString(5, user.getEmail());

            int rowsAffected = ps.executeUpdate();
            log.info("Успешно вставлено {} строк", rowsAffected);
            return rowsAffected;

        } catch (SQLException e) {
            log.error("Ошибка при вставке: {}", user.getLogin(), e);
            return 0;
        }
    }
}
