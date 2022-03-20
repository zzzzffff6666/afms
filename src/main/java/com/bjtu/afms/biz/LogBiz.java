package com.bjtu.afms.biz;

import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.OperationType;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Log;
import com.bjtu.afms.web.param.query.LogQueryParam;

public interface LogBiz {

    Page<Log> getLogList(LogQueryParam param, Integer page);

    void saveLog(DataType dataType, int relateId, OperationType operationType, String oldValue, String newValue);

    void saveLog(DataType dataType, int relateId, int userId, OperationType operationType, String oldValue, String newValue);
}
