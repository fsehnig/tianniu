package com.tcl.shbc.common.mongo;

import com.mongodb.Cursor;

/**
 * Created by Administrator on 2015/4/26.
 */
public interface CursorHandle {
    public void handle(Cursor cursor);
}
