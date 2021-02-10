package az.javafx.service.impl;

import az.javafx.dao.UserDao;
import az.javafx.model.Credential;
import az.javafx.service.LoginService;

public class LoginServiceImpl implements LoginService {
    private UserDao userDAO;

    public LoginServiceImpl(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public boolean login(Credential credential) {
        Credential credentialFromDb = userDAO.getUserByUsername(credential.getUsername());
        if (credential != null) {
            if (credential.getUsername().equals(credentialFromDb.getUsername()) &&
                    credential.getPassword().equals(credentialFromDb.getPassword())) {
                return true;
            }
        }


        return false;
    }
}
