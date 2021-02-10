package az.javafx.dao;

import az.javafx.model.Credential;
import az.javafx.model.User;

public interface UserDao {
    boolean addUser(User user);
  Credential getUserByUsername(String username);
}
