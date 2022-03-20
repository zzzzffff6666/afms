package com.bjtu.afms.biz;

import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Fund;
import com.bjtu.afms.web.param.query.FundQueryParam;

public interface FundBiz {

    Page<Fund> getFundList(FundQueryParam param, Integer page);

    Page<Fund> getMyFundList(Integer page);

    boolean insertFund(Fund fund);

    boolean modifyFundInfo(Fund fund);

    boolean deleteFund(int fundId);
}
