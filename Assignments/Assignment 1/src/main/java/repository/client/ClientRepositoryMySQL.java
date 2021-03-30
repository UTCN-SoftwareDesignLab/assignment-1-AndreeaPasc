package repository.client;

import model.Client;
import model.builder.ClientBuilder;
import repository.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.CLIENT;

public class ClientRepositoryMySQL implements ClientRepository{

    private final Connection connection;

    public ClientRepositoryMySQL (Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clientsInfo = new ArrayList<Client>();
        try{
            Statement statement = connection.createStatement();
            String fetchClientSql = "Select * from client_info";
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            while(clientResultSet.next()){
                Client client = new ClientBuilder()
                        .setId(clientResultSet.getLong("id"))
                        .setName(clientResultSet.getString("name"))
                        .setAddress(clientResultSet.getString("address"))
                        .setIdentificationNumber(clientResultSet.getLong("idCardNumber"))
                        .setPhoneNumber(clientResultSet.getLong("phoneNumber"))
                        .setPersonalNumericalCode(clientResultSet.getLong("PNC"))
                        .build();

                clientsInfo.add(client);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return clientsInfo;
    }

    @Override
    public boolean save(Client client) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO client_info values (null, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, client.getIdentificationNumber().toString());
            insertUserStatement.setString(2, client.getPersonalNumericalCode().toString());
            insertUserStatement.setString(3, client.getAddress());
            insertUserStatement.setString(4, client.getName());
            insertUserStatement.setString(5, client.getPhoneNumber().toString());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            client.setId(userId);

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
    public void update(Client oldClient, Client newClient) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE " + CLIENT + " SET idCardNumber = ?, PNC = ?, address = ?, name = ?, phoneNumber = ? WHERE id = " + oldClient.getId());

            preparedStatement.setString(4, newClient.getName());
            preparedStatement.setLong(1, newClient.getIdentificationNumber());
            preparedStatement.setLong(2, newClient.getPersonalNumericalCode());
            preparedStatement.setString(3, newClient.getAddress());
            preparedStatement.setLong(5, newClient.getPhoneNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(Client client) {
        try{
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("DELETE FROM client_info WHERE  " + "id = ?");
            insertUserStatement.setString(1, client.getId().toString());
            insertUserStatement.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Client findById(Long id) throws EntityNotFoundException {
        try{
            Statement statement = connection.createStatement();
            String fetchClientSql = "SELECT * FROM client_info WHERE id = " + id;
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            if(clientResultSet.next()){
                return new ClientBuilder()
                        .setId(clientResultSet.getLong("id"))
                        .setName(clientResultSet.getString("name"))
                        .setAddress(clientResultSet.getString("address"))
                        .setIdentificationNumber(clientResultSet.getLong("idCardNumber"))
                        .setPhoneNumber(clientResultSet.getLong("phoneNumber"))
                        .setPersonalNumericalCode(clientResultSet.getLong("PNC"))
                        .build();
            }else {
                throw new EntityNotFoundException(id, Client.class.getSimpleName());
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new EntityNotFoundException(id, Client.class.getSimpleName());
        }
    }

    public Client findByPNC(Long pnc) throws EntityNotFoundException{
        try{
            Statement statement = connection.createStatement();
            String fetchClientSql = "Select * from client_info WHERE PNC = " + pnc;
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            if(clientResultSet.next()){
                return new ClientBuilder()
                        .setId(clientResultSet.getLong("id"))
                        .setName(clientResultSet.getString("name"))
                        .setAddress(clientResultSet.getString("address"))
                        .setIdentificationNumber(clientResultSet.getLong("idCardNumber"))
                        .setPhoneNumber(clientResultSet.getLong("phoneNumber"))
                        .setPersonalNumericalCode(clientResultSet.getLong("PNC"))
                        .build();
            }else {
            throw new EntityNotFoundException(pnc, Client.class.getSimpleName());
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new EntityNotFoundException(pnc, Client.class.getSimpleName());
        }
    }
}
