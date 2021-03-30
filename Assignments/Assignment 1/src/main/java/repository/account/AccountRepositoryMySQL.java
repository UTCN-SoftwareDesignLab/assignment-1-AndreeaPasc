package repository.account;

import model.Account;
import model.builder.AccountBuilder;
import repository.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.ACCOUNT;

public class AccountRepositoryMySQL implements AccountRepository{

    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<Account>();
        try{
            Statement statement = connection.createStatement();
            String fetchAccountSql = "Select * from account";
            ResultSet accountResultSet = statement.executeQuery(fetchAccountSql);
            while(accountResultSet.next()){
                Account account = new AccountBuilder()
                        .setId(accountResultSet.getLong("id"))
                        .setClientID(accountResultSet.getLong("client_id"))
                        .setIdentificationNumber(accountResultSet.getLong("idNumber"))
                        .setCreationDate(accountResultSet.getDate("creationDate"))
                        .setMoneyAmount(accountResultSet.getDouble("moneyAmount"))
                        .setType(accountResultSet.getString("type"))
                        .build();

                accounts.add(account);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public boolean save(Account account) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO account values (null, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setLong(1, account.getClientId());
            insertUserStatement.setLong(2, account.getIdentificationNumber());
            insertUserStatement.setString(3, account.getType());
            insertUserStatement.setDouble(4, account.getMoneyAmount());
            insertUserStatement.setDate(5, new java.sql.Date(account.getCreationDate().getTime()));
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            account.setId(userId);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from account where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Account oldAccount, Account newAccount) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE " + ACCOUNT + " SET client_id = ?, idNumber = ?, type = ?, moneyAmount = ?, creationDate = ? WHERE id = " + oldAccount.getId());

            preparedStatement.setDouble(4, newAccount.getMoneyAmount());
            preparedStatement.setLong(1, newAccount.getClientId());
            preparedStatement.setLong(2, newAccount.getIdentificationNumber());
            preparedStatement.setString(3, newAccount.getType());
            preparedStatement.setDate(5, new java.sql.Date(newAccount.getCreationDate().getTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(Account account) {
        try{
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("DELETE FROM account WHERE  " + "id = ?");
            insertUserStatement.setString(1, account.getId().toString());
            insertUserStatement.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Account findById(Long id) throws EntityNotFoundException {
        try {
            Statement statement = connection.createStatement();
            String fetchAccountSql = "Select * from account WHERE id = " + id;
            ResultSet accountResultSet = statement.executeQuery(fetchAccountSql);
            if (accountResultSet.next()) {
                return new AccountBuilder()
                        .setId(accountResultSet.getLong("id"))
                        .setClientID(accountResultSet.getLong("client_id"))
                        .setIdentificationNumber(accountResultSet.getLong("idNumber"))
                        .setCreationDate(accountResultSet.getDate("creationDate"))
                        .setMoneyAmount(accountResultSet.getDouble("moneyAmount"))
                        .setType(accountResultSet.getString("type"))
                        .build();
            } else {
                throw new EntityNotFoundException(id, Account.class.getSimpleName());
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new EntityNotFoundException(id, Account.class.getSimpleName());
        }
    }

    @Override
    public Account findByIdNumber(Long id) throws EntityNotFoundException {
        try {
            Statement statement = connection.createStatement();
            String fetchAccountSql = "Select * from account WHERE idNumber = " + id;
            ResultSet accountResultSet = statement.executeQuery(fetchAccountSql);
            if (accountResultSet.next()) {
                return new AccountBuilder()
                        .setId(accountResultSet.getLong("id"))
                        .setClientID(accountResultSet.getLong("client_id"))
                        .setIdentificationNumber(accountResultSet.getLong("idNumber"))
                        .setCreationDate(accountResultSet.getDate("creationDate"))
                        .setMoneyAmount(accountResultSet.getDouble("moneyAmount"))
                        .setType(accountResultSet.getString("type"))
                        .build();
            } else {
                throw new EntityNotFoundException(id, Account.class.getSimpleName());
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new EntityNotFoundException(id, Account.class.getSimpleName());
        }
    }
}
