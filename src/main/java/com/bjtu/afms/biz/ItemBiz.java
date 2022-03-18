package com.bjtu.afms.biz;

import com.bjtu.afms.enums.ItemStatus;
import com.bjtu.afms.enums.ItemType;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Item;
import com.bjtu.afms.web.param.query.ItemQueryParam;

public interface ItemBiz {

    Page<Item> getItemList(ItemQueryParam param, Integer page);

    boolean statusChange(int itemId, ItemType itemType, ItemStatus newStatus);

    boolean takeFeedOrMedicine(int itemId, ItemType itemType, int amount);

    boolean insertItem(Item item);

    boolean deleteItem(int itemId);
}
