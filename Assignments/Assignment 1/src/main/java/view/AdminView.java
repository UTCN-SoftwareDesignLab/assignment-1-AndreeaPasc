package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdminView extends JFrame {
    JTextField tfUserId;
    JTextField tfUserUsername;
    JTextField tfUserPassword;
    //JTextField tfUserRoles;

    JButton btnSaveUser;
    JButton btnDeleteUser;
    JButton btnFindByIdUser;
    JButton btnFindAllUser;
    JButton btnRemoveAllUser;
    JButton btnUpdateUser;
    JButton btnFindByUsernameAndPasswordUser;

    public AdminView() throws HeadlessException{
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));

        add(tfUserId);
        add(tfUserUsername);
        add(tfUserPassword);
        //add(tfUserRoles);

        add(btnSaveUser);
        add(btnFindByIdUser);
        add(btnFindAllUser);
        add(btnDeleteUser);
        add(btnRemoveAllUser);
        add(btnUpdateUser);
        add(btnFindByUsernameAndPasswordUser);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields(){
        btnSaveUser = new JButton("Save new user");
        btnDeleteUser = new JButton("Delete user");
        btnUpdateUser = new JButton("Update existing user");
        btnFindAllUser = new JButton("Show all users");
        btnFindByIdUser = new JButton("Find user by Id");
        btnRemoveAllUser = new JButton("Delete all users");
        btnFindByUsernameAndPasswordUser = new JButton("Find by username and password");

        tfUserId = new JTextField();
        tfUserPassword = new JTextField();
        tfUserPassword = new JTextField();
        //tfUserRoles = new JTextField();
    }

    public Long getId(){
        return Long.parseLong(tfUserId.getText());
    }

    public String getUsername(){
        return tfUserUsername.getText();
    }

    public String getPassword(){
        return tfUserPassword.getText();
    }

    /*
    public String getRoles(){
        return tfUserRoles.getText();
    }
     */

    public void setFindAllUserButtonListener(ActionListener findAllUserButtonListener){
        btnFindAllUser.addActionListener(findAllUserButtonListener);
    }

    public void setSaveUserButtonListener(ActionListener saveUserButtonListener){
        btnSaveUser.addActionListener(saveUserButtonListener);
    }

    public void setRemoveUserButtonListener(ActionListener removeUserButtonListener){
        btnDeleteUser.addActionListener(removeUserButtonListener);
    }

    public void setUpdateUserButtonListener(ActionListener updateUserButtonListener){
        btnUpdateUser.addActionListener(updateUserButtonListener);
    }

    public void setRemoveAllUserButtonListener(ActionListener removeAllUserButtonListener){
        btnRemoveAllUser.addActionListener(removeAllUserButtonListener);
    }

    public void setFindByUsernameAndPasswordButtonListener(ActionListener findByUsernameAndPasswordButtonListener){
        btnFindByUsernameAndPasswordUser.addActionListener(findByUsernameAndPasswordButtonListener);
    }

    public void setVisible() {
        this.setVisible(true);
    }
}
