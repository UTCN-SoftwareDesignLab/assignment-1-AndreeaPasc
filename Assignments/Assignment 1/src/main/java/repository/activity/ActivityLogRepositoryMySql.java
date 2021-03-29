package repository.activity;

import model.ActivityLog;
import model.builder.ActivityLogBuilder;
import repository.EntityNotFoundException;
import repository.user.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityLogRepositoryMySql implements ActivityLogRepository {

    private final Connection connection;
    private final UserRepository userRepository;

    public ActivityLogRepositoryMySql(Connection connection, UserRepository userRepository) {
        this.connection = connection;
        this.userRepository = userRepository;
    }

    @Override
    public ActivityLog findById(Long id) throws EntityNotFoundException {
        try {
            Statement statement = connection.createStatement();
            String fetchActivitySql = "Select * from activity WHERE id = " + id;
            ResultSet activityResultSet = statement.executeQuery(fetchActivitySql);
            if (activityResultSet.next()) {
                return new ActivityLogBuilder()
                        .setId(activityResultSet.getLong("id"))
                        .setActivity(activityResultSet.getString("activity"))
                        .setUser(userRepository.findById(activityResultSet.getLong("user_id")))
                        .setDate(activityResultSet.getDate("date"))
                        .build();
            } else {
                throw new EntityNotFoundException(id, ActivityLog.class.getSimpleName());
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new EntityNotFoundException(id, ActivityLog.class.getSimpleName());
        }
    }

    @Override
    public boolean save(ActivityLog activityLog) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO activity values (null, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setLong(1, activityLog.getUser().getId());
            insertUserStatement.setString(2, activityLog.getActivity());
            insertUserStatement.setDate(3, new java.sql.Date(activityLog.getDate().getTime()));
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            activityLog.setId(userId);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ActivityLog> findAll() throws EntityNotFoundException {
        List<ActivityLog> activities = new ArrayList<ActivityLog>();
        try{
            Statement statement = connection.createStatement();
            String fetchActivitySql = "Select * from activity";
            ResultSet activityResultSet = statement.executeQuery(fetchActivitySql);
            while(activityResultSet.next()){
                ActivityLog activityLog =  new ActivityLogBuilder()
                        .setId(activityResultSet.getLong("id"))
                        .setActivity(activityResultSet.getString("activity"))
                        .setUser(userRepository.findById(activityResultSet.getLong("user_id")))
                        .setDate(activityResultSet.getDate("date"))
                        .build();

                activities.add(activityLog);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return activities;
    }
}
