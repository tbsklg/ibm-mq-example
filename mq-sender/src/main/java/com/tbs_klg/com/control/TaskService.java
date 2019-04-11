package com.tbs_klg.com.control;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import java.util.concurrent.locks.LockSupport;

@Singleton
public class TaskService {

    @Inject
    MessageProducer messageProducer;

    public TaskService() {
    }

    public Integer retrieveTask(String identifier) {
        if (TaskCache.getInstance().getTask(identifier) == null) {
            messageProducer.sendMessage(identifier);
            LockSupport.parkNanos(2_000_000_000L);
            return TaskCache.getInstance().getTask(identifier);
        }
        return TaskCache.getInstance().getTask(identifier);
    }

    public boolean containsKey(String identifier){
        return TaskCache.getInstance().contains(identifier);
    }

    public JsonObject createStatusPayload(String status){
        return Json.createObjectBuilder().add("status", status).build();
    }
}
