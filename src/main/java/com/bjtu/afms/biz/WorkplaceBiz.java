package com.bjtu.afms.biz;

import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Workplace;
import com.bjtu.afms.web.param.query.WorkplaceQueryParam;

public interface WorkplaceBiz {

    Page<Workplace> getWorkplaceList(WorkplaceQueryParam param, Integer page);

    boolean insertWorkplace(Workplace workplace);

    boolean modifyWorkplaceInfo(Workplace workplace);

    void modifyWorkplacePoolNum(int id, int addition);

    boolean deleteWorkplace(int workplaceId);
}
