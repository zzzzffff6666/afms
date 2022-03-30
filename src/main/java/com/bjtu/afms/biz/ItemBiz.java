package com.bjtu.afms.biz;

import com.bjtu.afms.enums.ItemStatus;
import com.bjtu.afms.enums.ItemType;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Item;
import com.bjtu.afms.web.param.query.ItemQueryParam;
import com.bjtu.afms.web.vo.ItemTokenVO;

public interface ItemBiz {

    Page<Item> getItemList(ItemQueryParam param, Integer page);

    Page<ItemTokenVO> getMyTakeLog(Integer page);

    boolean statusChange(int itemId, ItemType itemType, ItemStatus newStatus);

    boolean takeItem(int itemId, ItemType itemType, int amount);

    boolean returnItem(int itemId, int amount);

    boolean insertItem(Item item);

    boolean modifyItemInfo(Item item);

    boolean deleteItem(int itemId);
}
