package service.activity;

import model.ActivityLog;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.Date;
import java.util.List;

public interface ActivityLogService {
    ActivityLog findById(Long id) throws EntityNotFoundException;

    boolean save(ActivityLog activityLog);

    List<ActivityLog> findAll() throws EntityNotFoundException;

    boolean checkDateRange(Date startDate, Date stopDate, ActivityLog activityLog);
}
