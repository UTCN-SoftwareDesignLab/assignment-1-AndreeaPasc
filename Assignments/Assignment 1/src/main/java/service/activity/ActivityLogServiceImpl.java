package service.activity;

import model.ActivityLog;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.activity.ActivityLogRepository;

import java.util.Date;
import java.util.List;

public class ActivityLogServiceImpl implements ActivityLogService{

    private final ActivityLogRepository activityLogRepository;

    public ActivityLogServiceImpl(ActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    @Override
    public ActivityLog findById(Long id) throws EntityNotFoundException {
        return activityLogRepository.findById(id);
    }

    @Override
    public boolean save(ActivityLog activityLog) {
        return activityLogRepository.save(activityLog);
    }

    @Override
    public List<ActivityLog> findAll() throws EntityNotFoundException {
        return activityLogRepository.findAll();
    }

    @Override
    public boolean checkDateRange(Date startDate, Date stopDate, ActivityLog activityLog) {
        return activityLog.getDate().after(startDate) && activityLog.getDate().before(stopDate);
    }
}
