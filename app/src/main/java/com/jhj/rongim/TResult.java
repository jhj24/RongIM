package com.jhj.rongim;

import com.jhj.httplibrary.result.Result;

/**
 * Created by jhj on 18-6-9.
 */

public class TResult<T> extends Result {

    private T items;
    private int code;

    public T getItems() {
        return items;
    }

    public void setItems(T items) {
        this.items = items;
    }
}
