package repository.activity;

import model.ActivityLog;
import repository.EntityNotFoundException;

import java.util.List;

public interface ActivityLogRepository {

    ActivityLog findById(Long id) throws EntityNotFoundException;

    boolean save(ActivityLog activityLog);

    List<ActivityLog> findAll() throws EntityNotFoundException;

}
