package com.bjtu.afms.biz;

import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Alert;
import com.bjtu.afms.web.param.query.AlertQueryParam;

public interface AlertBiz {

    Page<Alert> getAlertList(AlertQueryParam param, Integer page);

    Page<Alert> getMyAlertList(Integer page);

    boolean insertAlert(Alert alert);

    boolean modifyAlertInfo(Alert alert);

    boolean modifyAlertUser(int id, int userId);

    boolean modifyAlertStatus(int id, int status);

    boolean deleteAlert(int alertId);
}
