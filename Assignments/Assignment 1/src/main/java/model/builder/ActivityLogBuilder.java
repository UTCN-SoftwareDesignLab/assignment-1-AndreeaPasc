package model.builder;

import model.ActivityLog;
import model.User;

import java.util.Date;

public class ActivityLogBuilder {
    private ActivityLog activityLog;

    public ActivityLogBuilder(){
        activityLog = new ActivityLog();
    }

    public ActivityLogBuilder setId(Long id){
        activityLog.setId(id);
        return this;
    }

    public ActivityLogBuilder setActivity(String activity){
        activityLog.setActivity(activity);
        return this;
    }

    public ActivityLogBuilder setDate(Date date){
        activityLog.setDate(date);
        return this;
    }

    public ActivityLogBuilder setUser(User user){
        activityLog.setUser(user);
        return this;
    }

    public ActivityLog build(){
        return activityLog;
    }
}
