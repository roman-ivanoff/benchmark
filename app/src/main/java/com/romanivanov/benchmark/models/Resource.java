package com.romanivanov.benchmark.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Resource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final T data;

    public final int messageId;

    private Resource(@NonNull Status status, @Nullable T data, int messageId) {
        this.status = status;
        this.data = data;
        this.messageId = messageId;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(Status.SUCCESS, data, -1);
    }

    public static <T> Resource<T> error(int msgId, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, msgId);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, -1);
    }

    public enum Status { SUCCESS, ERROR, LOADING }
}
