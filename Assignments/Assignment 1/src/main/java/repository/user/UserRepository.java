package repository.user;

import model.User;
import model.validation.Notification;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    Notification<User> findByUsernameAndPassword(String username, String password);

    boolean save(User user);

    void removeAll();

    public boolean update(User user);

    public boolean delete(User user);
}
