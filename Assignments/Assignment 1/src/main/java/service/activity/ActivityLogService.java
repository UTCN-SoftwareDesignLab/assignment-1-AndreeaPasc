package service.activity;

import model.ActivityLog;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.Date;

public interface ActivityLogService {
    ActivityLog findById(Long id) throws EntityNotFoundException;

    boolean save(ActivityLog activityLog);

    Notification<Boolean> findAll() throws EntityNotFoundException;

    boolean checkDateRange(Date startDate, Date stopDate, ActivityLog activityLog);
}
