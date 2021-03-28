package repository.user;

import model.User;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    Notification<User> findByUsernameAndPassword(String username, String password);

    boolean save(User user);

    void removeAll();

    void update(User oldUser, User newUser);

    boolean delete(User user);

    User findById(Long id) throws EntityNotFoundException;
}
