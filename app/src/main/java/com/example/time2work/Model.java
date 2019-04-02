package com.example.time2work;


public class Model {

    private String workAddress;
    private String homeAddress;
    private int timeToArriveAtWork;
    private int timeToGetReady;

    public Model(String workAddress, String homeAddress, int timeToArriveAtWork, int timeToGetReady)
    {
        this.workAddress = workAddress;
        this.homeAddress = homeAddress;
        this.timeToArriveAtWork = timeToArriveAtWork;
        this.timeToGetReady = timeToGetReady;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public int getTimeToArriveAtWork() {
        return timeToArriveAtWork;
    }

    public void setTimeToArriveAtWork(int timeToArriveAtWork) {
        this.timeToArriveAtWork = timeToArriveAtWork;
    }

    public int getTimeToGetReady() {
        return timeToGetReady;
    }

    public void setTimeToGetReady(int timeToGetReady) {
        this.timeToGetReady = timeToGetReady;
    }
}
