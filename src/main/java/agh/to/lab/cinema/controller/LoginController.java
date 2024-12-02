package agh.to.lab.cinema.controller;

import agh.to.lab.cinema.app.CinemaApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginController {
    // Login view labels, textFields etc.
    @FXML
    TextField usernameTextFieldLogin;
    @FXML
    PasswordField passwordTextFieldLogin;
    @FXML
    Label usernameLabelLogin;
    @FXML
    Label passwordLabelLogin;
    @FXML
    Label loginResultLabel;


    // Login view functions
    @FXML
    private String sendLoginPostRequest() throws Exception {
        loginResultLabel.setVisible(false);

        System.out.println(JsonBodyCreator.createCinemaUserBody(usernameTextFieldLogin.getText(), passwordTextFieldLogin.getText(), "sdfgdd"));
        String url = "http://localhost:8080/api/login";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(JsonBodyCreator.createCinemaUserBody(usernameTextFieldLogin.getText(), passwordTextFieldLogin.getText(), null)))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response body: " + response.body());
        System.out.println(usernameTextFieldLogin.getText() + " " + passwordTextFieldLogin.getText());
        if (response.body().equals("User logged in")) {
            loginResultLabel.setVisible(true);
            loginResultLabel.setText("You have successfully logged in!");
            loginResultLabel.setTextFill(Color.color(0, 1.0, 0));
        }
        else if (response.body().equals("User not found")) {
            loginResultLabel.setVisible(true);
            loginResultLabel.setText("Username or password is incorrect");
            loginResultLabel.setTextFill(Color.color(1.0, 0, 0));
        }
        else {
            loginResultLabel.setVisible(true);
            loginResultLabel.setText("UNKNOWN ERROR");
            loginResultLabel.setTextFill(Color.color(1.0, 0, 0));
        }

        return response.body();
    }
    @FXML
    private void goToRegister() {
        CinemaApp.loadView("views/register.fxml");
    }

    // Not used
    @FXML
    private void resetUsernameLabelLogin() {
        usernameLabelLogin.setVisible(false);
    }
    @FXML
    private void resetPasswordLabelLogin() {
        passwordLabelLogin.setVisible(false);
    }
}