package service.user;

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
        UserValidator userValidator = null;
        boolean valid = false;
        List<User> users = userRepository.findAll();
        for(User user: users) {
            userValidator = new UserValidator(user);
            valid = userValidator.validate();
        }
        Notification<Boolean> userNotification = new Notification<>();
        if(valid){
            userNotification.setResult(!userRepository.findAll().isEmpty());
        }else{
            assert userValidator != null;
            userValidator.getErrors().forEach(userNotification::addError);
            userNotification.setResult(Boolean.FALSE);
        }
        return userNotification;
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
    public Notification<Boolean> delete(User user) {
        UserValidator userValidator = new UserValidator(user);
        boolean valid = userValidator.validate();
        Notification<Boolean> userNotification = new Notification<>();
        if(valid){
            userNotification.setResult(userRepository.delete(user));
        }else{
            userValidator.getErrors().forEach(userNotification::addError);
            userNotification.setResult(Boolean.FALSE);
        }
        return userNotification;
    }

    @Override
    public Notification<Boolean> findById(User user) throws EntityNotFoundException {
        UserValidator userValidator = new UserValidator(user);
        boolean valid = userValidator.validate();
        Notification<Boolean> userNotification = new Notification<>();
        List<User> users = null;
        if(valid){
            users.add(userRepository.findById(user));
            userNotification.setResult(!users.isEmpty());
        }else{
            userValidator.getErrors().forEach(userNotification::addError);
            userNotification.setResult(Boolean.FALSE);
        }
        return userNotification;
    }
}
