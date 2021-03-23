package repository.client;

import model.ClientInfo;
import model.User;
import model.builder.ClientInfoBuilder;
import model.builder.UserBuilder;
import repository.security.RightsRolesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryMySQL implements ClientRepository{

    private final Connection connection;

    public ClientRepositoryMySQL (Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<ClientInfo> findAll() {
        List<ClientInfo> clientsInfo = new ArrayList<ClientInfo>();
        try{
            Statement statement = connection.createStatement();
            String fetchClientSql = "Select * from client_info";
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            while(clientResultSet.next()){
                ClientInfo clientInfo = new ClientInfoBuilder()
                        .setName(clientResultSet.getString("name"))
                        .setAddress(clientResultSet.getString("address"))
                        .setIdentificationNumber(clientResultSet.getLong("idCardNumber"))
                        .setPhoneNumber(clientResultSet.getLong("phoneNumber"))
                        .setPersonalNumericalCode(clientResultSet.getLong("PNC"))
                        .build();

                clientsInfo.add(clientInfo);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return clientsInfo;
    }

    @Override
    public boolean save(ClientInfo clientInfo) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO client_info values (null, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, clientInfo.getIdentificationNumber().toString());
            insertUserStatement.setString(2, clientInfo.getPersonalNumericalCode().toString());
            insertUserStatement.setString(3, clientInfo.getAddress());
            insertUserStatement.setString(4, clientInfo.getName());
            insertUserStatement.setString(5, clientInfo.getPhoneNumber().toString());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            clientInfo.setId(userId);

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
            String sql = "DELETE from client_info where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(ClientInfo clientInfo) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("UPDATE client_info SET  " + "name = ?, phoneNumber = ?, idCardNumber = ?, PNC = ?, address = ?, " + "WHERE  id = ?");
            insertUserStatement.setString(1, clientInfo.getIdentificationNumber().toString());
            insertUserStatement.setString(2, clientInfo.getPersonalNumericalCode().toString());
            insertUserStatement.setString(3, clientInfo.getAddress());
            insertUserStatement.setString(4, clientInfo.getName());
            insertUserStatement.setString(5, clientInfo.getPhoneNumber().toString());
            insertUserStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(ClientInfo clientInfo) {
        try{
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("DELETE FROM client_info WHERE  " + "id = ?");
            insertUserStatement.setString(1, clientInfo.getId().toString());
            insertUserStatement.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ClientInfo findById(ClientInfo clientInfo) {
        ClientInfo newClientInfo = null;
        try{
            Statement statement = connection.createStatement();
            String fetchClientSql = "Select * from client_info WHERE id = " + clientInfo.getId();
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            while(clientResultSet.next()){
                newClientInfo = new ClientInfoBuilder()
                        .setName(clientResultSet.getString("name"))
                        .setAddress(clientResultSet.getString("address"))
                        .setIdentificationNumber(clientResultSet.getLong("idCardNumber"))
                        .setPhoneNumber(clientResultSet.getLong("phoneNumber"))
                        .setPersonalNumericalCode(clientResultSet.getLong("PNC"))
                        .build();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return newClientInfo;
    }

    public ClientInfo findByPNC(ClientInfo clientInfo){
        ClientInfo newClientInfo = null;
        try{
            Statement statement = connection.createStatement();
            String fetchClientSql = "Select * from client_info WHERE id = " + clientInfo.getPersonalNumericalCode();
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            while(clientResultSet.next()){
                newClientInfo = new ClientInfoBuilder()
                        .setName(clientResultSet.getString("name"))
                        .setAddress(clientResultSet.getString("address"))
                        .setIdentificationNumber(clientResultSet.getLong("idCardNumber"))
                        .setPhoneNumber(clientResultSet.getLong("phoneNumber"))
                        .setPersonalNumericalCode(clientResultSet.getLong("PNC"))
                        .build();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return newClientInfo;
    }

}
