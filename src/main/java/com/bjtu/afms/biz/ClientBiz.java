package com.bjtu.afms.biz;

import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Client;
import com.bjtu.afms.web.param.query.ClientQueryParam;

public interface ClientBiz {

    Page<Client> getClientList(ClientQueryParam param, Integer page);

    boolean insertClient(Client client);
}
