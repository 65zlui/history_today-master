package com.animee.todayhistory.network;

import java.io.IOException;

import okhttp3.Response;

public interface OkHttpCallBack {
    void onFailure(IOException e);
    void onSuccess(Response response);
}
