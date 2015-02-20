package com.oracle.ws;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;

public class AsyncHandlerService {

    private AsyncHandler _handler;
    private Executor _executor;
    private WSFuture wsfuture;
    private Response response;

    public AsyncHandlerService(AsyncHandler handler, Executor executor) {

        _handler = handler;
        _executor = executor;
    }


    public void executeWSFuture() {

        _executor.execute((Runnable) wsfuture);
    }

    public WSFuture<Object>setupAsyncCallback(
                       final Response<Object>result) {
        response = result;

        wsfuture = new WSFuture<Object>(new Callable<
                                        Object>() {

            public Object call() throws Exception {
                _handler.handleResponse(response);
                return null;
            }
        });
        return wsfuture;
    }
}