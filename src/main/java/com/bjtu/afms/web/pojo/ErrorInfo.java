package com.bjtu.afms.web.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorInfo {
    private Integer successNum;
    private Integer failedNum;
    private List<String> errorMsgList;

    public ErrorInfo() {
        successNum = 0;
        failedNum = 0;
    }

    public void failedAdd(String errorMsg) {
        failedNum++;
        if (errorMsgList == null) {
            errorMsgList = new ArrayList<>();
        }
        errorMsgList.add(errorMsg);
    }

    public void successAdd() {
        successNum++;
    }
}
