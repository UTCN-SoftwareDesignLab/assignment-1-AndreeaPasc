package service.user;

import model.User;
import model.validation.Notification;
import repository.EntityNotFoundException;

public interface UserService {
    Notification<Boolean> findAll();

    Notification<User> findByUsernameAndPassword(String username, String password);

    Notification<Boolean> save(User user);

    Notification<Boolean> removeAll();

    void update(User oldUser, User newUser);

    Notification<Boolean> delete(User user);

    Notification<Boolean> findById(User user) throws EntityNotFoundException;
}
