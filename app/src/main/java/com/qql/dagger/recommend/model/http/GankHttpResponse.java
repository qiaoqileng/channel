package com.qql.dagger.recommend.model.http;

/**
 * Created by codeest on 2016/8/3.
 */
public class GankHttpResponse<T> {

    private boolean error;
    private T results;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    private String errorMessage;

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public boolean getError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
