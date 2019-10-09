package com.dwell.it.enums;

import java.util.List;

public enum DataSingleton {

    INSTANCE;

    private boolean isFetchingServiceRunning;
    private boolean isDatasourceFetched;
    private boolean shouldExportingExcel;

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

    public boolean isShouldExportingExcel() {
        return shouldExportingExcel;
    }

    public void setShouldExportingExcel(boolean shouldExportingExcel) {
        this.shouldExportingExcel = shouldExportingExcel;
    }

    public List<String> getRequestURLsList() {
        return requestURLsList;
    }

    public void setRequestURLsList(List<String> requestURLsList) {
        this.requestURLsList = requestURLsList;
    }
}
