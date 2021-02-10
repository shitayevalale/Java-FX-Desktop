package az.javafx.service.impl;

import az.javafx.dao.UserDao;
import az.javafx.model.Credential;
import az.javafx.model.User;
import az.javafx.service.RegistrationService;

public class RegistrationServiceImpl implements RegistrationService {
    private UserDao userDAO;

    public RegistrationServiceImpl(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public boolean addUser(User user) {
        return userDAO.addUser(user);
    }


    }

