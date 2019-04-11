package com.tbs_klg.com.control;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TaskCache {
    private Map<String, Integer> cache = new ConcurrentHashMap<>();
    private static final TaskCache taskCache = new TaskCache();

    public static TaskCache getInstance(){
        return taskCache;
    }

    public void addTask(String identifier, Integer task) {
        cache.put(identifier, task);
    }

    public Integer getTask(String identifier) {
        return cache.get(identifier);
    }

    public boolean contains(String identifier){
        return cache.containsKey(identifier);
    }
}