package com.dwell.it.enums;

import java.util.List;

public enum DataSingleton {

    INSTANCE;

    private boolean isFetchingServiceRunning;
    private boolean isDatasourceFetched;

    private List<String> requestURLsList;


    public boolean isFetchingServiceRunning() {
        return isFetchingServiceRunning;
    }

    public void setFetchingServiceRunning(boolean fetchingServiceRunning) {
        isFetchingServiceRunning = fetchingServiceRunning;
    }

    public boolean isDatasourceFetched() {
        return isDatasourceFetched;
    }

    public void setDatasourceFetched(boolean datasourceFetched) {
        isDatasourceFetched = datasourceFetched;
    }

    public List<String> getRequestURLsList() {
        return requestURLsList;
    }

    public void setRequestURLsList(List<String> requestURLsList) {
        this.requestURLsList = requestURLsList;
    }
}
