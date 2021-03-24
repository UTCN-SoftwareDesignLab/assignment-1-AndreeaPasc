package repository.account;

import model.Account;
import model.ClientInfo;
import model.builder.AccountBuilder;
import repository.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                        .setClientID(accountResultSet.getLong("client_id"))
                        .setIdentificationNumber(accountResultSet.getLong("idNumber"))
                        .setCreationDate(accountResultSet.getDate("creationDate"))
                        .setMoneyAmount(accountResultSet.getLong("moneyAmount"))
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
            insertUserStatement.setLong(4, account.getMoneyAmount());
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
    public boolean update(Account account) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("UPDATE account SET  " + "client_info = ?, idNumber = ?, type = ?, moneyAmount = ?, creationDate = ?" + "WHERE  id = ?");
            insertUserStatement.setString(1, account.getClientId().toString());
            insertUserStatement.setString(2, account.getIdentificationNumber().toString());
            insertUserStatement.setString(3, account.getType());
            insertUserStatement.setString(4, account.getMoneyAmount().toString());
            insertUserStatement.setString(5, account.getCreationDate().toString());
            insertUserStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
    public Account findById(Account account) throws EntityNotFoundException {
        try {
            Statement statement = connection.createStatement();
            String fetchAccountSql = "Select * from account WHERE id = " + account.getId();
            ResultSet accountResultSet = statement.executeQuery(fetchAccountSql);
            if (accountResultSet.next()) {
                return new AccountBuilder()
                        .setId(accountResultSet.getLong("id"))
                        .setClientID(accountResultSet.getLong("client_id"))
                        .setIdentificationNumber(accountResultSet.getLong("idNumber"))
                        .setCreationDate(accountResultSet.getDate("creationDate"))
                        .setMoneyAmount(accountResultSet.getLong("moneyAmount"))
                        .setType(accountResultSet.getString("type"))
                        .build();
            } else {
                throw new EntityNotFoundException(account.getId(), Account.class.getSimpleName());
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new EntityNotFoundException(account.getId(), Account.class.getSimpleName());
        }
    }
}
