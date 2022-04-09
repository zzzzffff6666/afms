package com.bjtu.afms.cron;

import com.bjtu.afms.enums.ItemStatus;
import com.bjtu.afms.mapper.ItemMapper;
import com.bjtu.afms.model.Item;
import com.bjtu.afms.model.ItemExample;
import com.bjtu.afms.model.Store;
import com.bjtu.afms.model.User;
import com.bjtu.afms.service.ItemService;
import com.bjtu.afms.service.StoreService;
import com.bjtu.afms.service.ToolService;
import com.bjtu.afms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ItemStatusCron {

    @Resource
    private ItemService itemService;

    @Resource
    private StoreService storeService;

    @Resource
    private UserService userService;

    @Resource
    private ToolService toolService;
    
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    // 更新过期状态
    @Scheduled(cron = "0 0 2 * * ?")
    public void updateExpire() {
        Date now = new Date();
        log.info("start cron job: updateExpire at time: {}", now.toString());
        try {
            ItemExample example = new ItemExample();
            example.createCriteria()
                    .andStatusEqualTo(ItemStatus.ACTIVE.getId())
                    .andExpireTimeLessThan(now);
            List<Item> itemList = itemService.selectItemList(example);
            int size = itemList.size();
            SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
            ItemMapper itemMapper = sqlSession.getMapper(ItemMapper.class);
            for (int i = 0; i < size; ++i) {
                Item record = new Item();
                record.setId(itemList.get(i).getId());
                record.setStatus(ItemStatus.EXPIRED.getId());
                itemMapper.updateByPrimaryKeySelective(record);
                if ((i + 1) % 300 == 0) {
                    sqlSession.commit();
                    sqlSession.clearCache();
                }
            }
            sqlSession.commit();
            sqlSession.clearCache();
            sqlSession.close();
            log.info("finish cron job: updateExpire at time: {}", new Date().toString());
        } catch (Exception e) {
            log.error("execute cron job: updateExpire meet exception: {}", e.getMessage());
        }
    }

    // 更新用尽状态
    @Scheduled(cron = "0 30 2 * * ?")
    public void updateDeplete() {
        Date now = new Date();
        log.info("start cron job: updateDeplete at time: {}", now.toString());
        try {
            ItemExample example = new ItemExample();
            example.createCriteria()
                    .andStatusEqualTo(ItemStatus.ACTIVE.getId())
                    .andAmountLessThan(0);
            List<Item> itemList = itemService.selectItemList(example);
            int size = itemList.size();
            SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
            ItemMapper itemMapper = sqlSession.getMapper(ItemMapper.class);
            for (int i = 0; i < size; ++i) {
                Item record = new Item();
                record.setId(itemList.get(i).getId());
                record.setStatus(ItemStatus.DEPLETED.getId());
                itemMapper.updateByPrimaryKeySelective(record);
                if ((i + 1) % 300 == 0) {
                    sqlSession.commit();
                    sqlSession.clearCache();
                }
            }
            sqlSession.commit();
            sqlSession.clearCache();
            sqlSession.close();
            log.info("finish cron job: updateDeplete at time: {}", new Date().toString());
        } catch (Exception e) {
            log.error("execute cron job: updateDeplete meet exception: {}", e.getMessage());
        }
    }

    // 提醒工具的维护
    @Scheduled(cron = "0 0 23 * * ?")
    public void remindMaintain() {
        Date now = new Date();
        log.info("start cron job: remindMaintain at time: {}", now.toString());
        try {
            ItemExample example = new ItemExample();
            example.createCriteria()
                    .andStatusEqualTo(ItemStatus.ACTIVE.getId())
                    .andMaintainTimeLessThan(now);
            List<Item> itemList = itemService.selectItemList(example);
            Map<Integer, Long> managerMap = itemList.stream().collect(Collectors.groupingBy(
                    Item::getStoreId,
                    Collectors.counting()));
            Map<String, String> param = new HashMap<>();
            param.put("remindName", "你负责仓库的部分工具需要维护");
            for (Map.Entry<Integer, Long> entry : managerMap.entrySet()) {
                int storeId = entry.getKey();
                Store store = storeService.selectStore(storeId);
                if (store == null) {
                    continue;
                }
                User user = userService.selectUser(store.getManager());
                if (user == null) {
                    continue;
                }
                param.put("message", String.format("在仓库%s中，总共%d种工具需要维护", store.getName(), entry.getValue()));
                toolService.sendRemind(user.getPhone(), param);
            }
            log.info("finish cron job: remindMaintain at time: {}", new Date().toString());
        } catch (Exception e) {
            log.error("execute cron job: remindMaintain meet exception: {}", e.getMessage());
        }
    }
}
