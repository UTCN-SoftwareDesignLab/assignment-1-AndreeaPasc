package service.user;

import model.Account;
import model.Client;
import model.User;
import model.validation.ClientValidator;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.EntityNotFoundException;
import repository.user.UserRepository;

import java.util.List;

public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Notification<Boolean> findAll() {
        List<User> users = userRepository.findAll();
        Notification<Boolean> accountNotification = new Notification<>();
        if(!users.isEmpty()){
            accountNotification.setResult(Boolean.TRUE);
        }else{
            accountNotification.setResult(Boolean.FALSE);
        }
        return accountNotification;
    }

    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public Notification<Boolean> save(User user) {
        UserValidator userValidator = new UserValidator(user);
        boolean valid = userValidator.validate();
        Notification<Boolean> userNotification = new Notification<>();
        if(valid){
            userNotification.setResult(userRepository.save(user));
        }else{
            userValidator.getErrors().forEach(userNotification::addError);
            userNotification.setResult(Boolean.FALSE);
        }
        return userNotification;
    }

    @Override
    public Notification<Boolean> removeAll() {
        userRepository.removeAll();
        Notification<Boolean> userNotification = new Notification<>();
        List<User> clients = userRepository.findAll();
        if(clients.isEmpty()){
            userNotification.setResult(Boolean.TRUE);
        }else{
            userNotification.setResult(Boolean.FALSE);
        }
        return userNotification;
    }

    @Override
    public void update(User oldUser, User newUser) {
        userRepository.update(oldUser, newUser);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public Notification<Boolean> findById(Long id) throws EntityNotFoundException {
        User user = userRepository.findById(id);
        Notification<Boolean> userNotification = new Notification<>();
        List<User> users = userRepository.findAll();
        users.add(user);
        if(!users.isEmpty()){
            userNotification.setResult(Boolean.TRUE);
        }else{
            userNotification.setResult(Boolean.FALSE);
        }

        return userNotification;
    }

    @Override
    public User findByUsername(String username) throws EntityNotFoundException {
        return userRepository.findByUsername(username);
    }
}
