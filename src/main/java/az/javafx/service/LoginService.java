package az.javafx.service;

import az.javafx.model.Credential;

public interface LoginService {
    boolean login(Credential credential);
}
