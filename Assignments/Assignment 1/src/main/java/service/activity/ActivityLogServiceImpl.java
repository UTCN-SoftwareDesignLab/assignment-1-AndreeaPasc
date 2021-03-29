package service.activity;

import model.ActivityLog;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.activity.ActivityLogRepository;

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
    public Notification<Boolean> findAll() throws EntityNotFoundException {
        List<ActivityLog> activityLogs = activityLogRepository.findAll();
        Notification<Boolean> activityNotification = new Notification<>();
        if(!activityLogs.isEmpty()){
            activityNotification.setResult(Boolean.TRUE);
        }else{
            activityNotification.setResult(Boolean.FALSE);
        }
        return activityNotification;
    }
}
