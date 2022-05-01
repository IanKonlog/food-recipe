package com.coding.Recipe4U.Classes.UserClasses;

import java.util.HashMap;

public class UserLog {

    //private ArrayList<String> searchQuery;
    private HashMap<String, Integer> logs;

    public UserLog() {
    }

    public UserLog(HashMap<String, Integer> logs) {
        this.logs = logs;
    }

    public HashMap<String, Integer> getLogs() {
        return logs;
    }

    public void setLogs(HashMap<String, Integer> logs) {
        this.logs = logs;
    }

    //private ArrayList<String> dishTypes;
    //private ArrayList<String> cuisines;

}