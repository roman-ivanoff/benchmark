package com.romanivanov.benchmark.models.assembly;

import androidx.annotation.NonNull;

import java.util.Objects;

public class AssemblyData {
    private final int operationNameId;
    private final int labelId;
    private final boolean isInProgress;
    private final String timeInMs;

    public AssemblyData(int labelId, int operationNameId, boolean isInProgress) {
        this.operationNameId = operationNameId;
        this.labelId = labelId;
        this.isInProgress = isInProgress;
        this.timeInMs = null;
    }

    public AssemblyData(int labelId, int operationNameId, boolean isInProgress, String timeInMs) {
        this.operationNameId = operationNameId;
        this.labelId = labelId;
        this.isInProgress = isInProgress;
        this.timeInMs = timeInMs;
    }

    public String getTimeInMs() {
        return timeInMs;
    }

    public int getOperationNameId() {
        return operationNameId;
    }

    public int getLabelId() {
        return labelId;
    }

    public boolean isInProgress() {
        return isInProgress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssemblyData that = (AssemblyData) o;
        return Objects.equals(timeInMs, that.timeInMs) && Objects.equals(labelId, that.labelId) &&
                Objects.equals(operationNameId, that.operationNameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeInMs, labelId, isInProgress);
    }

    @NonNull
    @Override
    public String toString() {
        return "CollectionModel{" + "timeInMs='" + timeInMs + '\'' +
                ", operationName='" + operationNameId + '\'' +
                ", listName='" + labelId + '\'' +
                ", isInProgress=" + isInProgress +
                '}';
    }

    public boolean isSameItemType(AssemblyData a) {
        return getOperationNameId() == a.getOperationNameId() &&
                getLabelId() == a.getLabelId();
    }
}
