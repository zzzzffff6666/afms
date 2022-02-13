package com.bjtu.afms.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PoolTaskExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PoolTaskExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPoolIdIsNull() {
            addCriterion("pool_id is null");
            return (Criteria) this;
        }

        public Criteria andPoolIdIsNotNull() {
            addCriterion("pool_id is not null");
            return (Criteria) this;
        }

        public Criteria andPoolIdEqualTo(Integer value) {
            addCriterion("pool_id =", value, "poolId");
            return (Criteria) this;
        }

        public Criteria andPoolIdNotEqualTo(Integer value) {
            addCriterion("pool_id <>", value, "poolId");
            return (Criteria) this;
        }

        public Criteria andPoolIdGreaterThan(Integer value) {
            addCriterion("pool_id >", value, "poolId");
            return (Criteria) this;
        }

        public Criteria andPoolIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("pool_id >=", value, "poolId");
            return (Criteria) this;
        }

        public Criteria andPoolIdLessThan(Integer value) {
            addCriterion("pool_id <", value, "poolId");
            return (Criteria) this;
        }

        public Criteria andPoolIdLessThanOrEqualTo(Integer value) {
            addCriterion("pool_id <=", value, "poolId");
            return (Criteria) this;
        }

        public Criteria andPoolIdIn(List<Integer> values) {
            addCriterion("pool_id in", values, "poolId");
            return (Criteria) this;
        }

        public Criteria andPoolIdNotIn(List<Integer> values) {
            addCriterion("pool_id not in", values, "poolId");
            return (Criteria) this;
        }

        public Criteria andPoolIdBetween(Integer value1, Integer value2) {
            addCriterion("pool_id between", value1, value2, "poolId");
            return (Criteria) this;
        }

        public Criteria andPoolIdNotBetween(Integer value1, Integer value2) {
            addCriterion("pool_id not between", value1, value2, "poolId");
            return (Criteria) this;
        }

        public Criteria andCycleIsNull() {
            addCriterion("cycle is null");
            return (Criteria) this;
        }

        public Criteria andCycleIsNotNull() {
            addCriterion("cycle is not null");
            return (Criteria) this;
        }

        public Criteria andCycleEqualTo(Integer value) {
            addCriterion("cycle =", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleNotEqualTo(Integer value) {
            addCriterion("cycle <>", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleGreaterThan(Integer value) {
            addCriterion("cycle >", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleGreaterThanOrEqualTo(Integer value) {
            addCriterion("cycle >=", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleLessThan(Integer value) {
            addCriterion("cycle <", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleLessThanOrEqualTo(Integer value) {
            addCriterion("cycle <=", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleIn(List<Integer> values) {
            addCriterion("cycle in", values, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleNotIn(List<Integer> values) {
            addCriterion("cycle not in", values, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleBetween(Integer value1, Integer value2) {
            addCriterion("cycle between", value1, value2, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleNotBetween(Integer value1, Integer value2) {
            addCriterion("cycle not between", value1, value2, "cycle");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andTaskIdIsNull() {
            addCriterion("task_id is null");
            return (Criteria) this;
        }

        public Criteria andTaskIdIsNotNull() {
            addCriterion("task_id is not null");
            return (Criteria) this;
        }

        public Criteria andTaskIdEqualTo(Integer value) {
            addCriterion("task_id =", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotEqualTo(Integer value) {
            addCriterion("task_id <>", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThan(Integer value) {
            addCriterion("task_id >", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("task_id >=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThan(Integer value) {
            addCriterion("task_id <", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThanOrEqualTo(Integer value) {
            addCriterion("task_id <=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdIn(List<Integer> values) {
            addCriterion("task_id in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotIn(List<Integer> values) {
            addCriterion("task_id not in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdBetween(Integer value1, Integer value2) {
            addCriterion("task_id between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotBetween(Integer value1, Integer value2) {
            addCriterion("task_id not between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andStartPreIsNull() {
            addCriterion("start_pre is null");
            return (Criteria) this;
        }

        public Criteria andStartPreIsNotNull() {
            addCriterion("start_pre is not null");
            return (Criteria) this;
        }

        public Criteria andStartPreEqualTo(Date value) {
            addCriterion("start_pre =", value, "startPre");
            return (Criteria) this;
        }

        public Criteria andStartPreNotEqualTo(Date value) {
            addCriterion("start_pre <>", value, "startPre");
            return (Criteria) this;
        }

        public Criteria andStartPreGreaterThan(Date value) {
            addCriterion("start_pre >", value, "startPre");
            return (Criteria) this;
        }

        public Criteria andStartPreGreaterThanOrEqualTo(Date value) {
            addCriterion("start_pre >=", value, "startPre");
            return (Criteria) this;
        }

        public Criteria andStartPreLessThan(Date value) {
            addCriterion("start_pre <", value, "startPre");
            return (Criteria) this;
        }

        public Criteria andStartPreLessThanOrEqualTo(Date value) {
            addCriterion("start_pre <=", value, "startPre");
            return (Criteria) this;
        }

        public Criteria andStartPreIn(List<Date> values) {
            addCriterion("start_pre in", values, "startPre");
            return (Criteria) this;
        }

        public Criteria andStartPreNotIn(List<Date> values) {
            addCriterion("start_pre not in", values, "startPre");
            return (Criteria) this;
        }

        public Criteria andStartPreBetween(Date value1, Date value2) {
            addCriterion("start_pre between", value1, value2, "startPre");
            return (Criteria) this;
        }

        public Criteria andStartPreNotBetween(Date value1, Date value2) {
            addCriterion("start_pre not between", value1, value2, "startPre");
            return (Criteria) this;
        }

        public Criteria andEndPreIsNull() {
            addCriterion("end_pre is null");
            return (Criteria) this;
        }

        public Criteria andEndPreIsNotNull() {
            addCriterion("end_pre is not null");
            return (Criteria) this;
        }

        public Criteria andEndPreEqualTo(Date value) {
            addCriterion("end_pre =", value, "endPre");
            return (Criteria) this;
        }

        public Criteria andEndPreNotEqualTo(Date value) {
            addCriterion("end_pre <>", value, "endPre");
            return (Criteria) this;
        }

        public Criteria andEndPreGreaterThan(Date value) {
            addCriterion("end_pre >", value, "endPre");
            return (Criteria) this;
        }

        public Criteria andEndPreGreaterThanOrEqualTo(Date value) {
            addCriterion("end_pre >=", value, "endPre");
            return (Criteria) this;
        }

        public Criteria andEndPreLessThan(Date value) {
            addCriterion("end_pre <", value, "endPre");
            return (Criteria) this;
        }

        public Criteria andEndPreLessThanOrEqualTo(Date value) {
            addCriterion("end_pre <=", value, "endPre");
            return (Criteria) this;
        }

        public Criteria andEndPreIn(List<Date> values) {
            addCriterion("end_pre in", values, "endPre");
            return (Criteria) this;
        }

        public Criteria andEndPreNotIn(List<Date> values) {
            addCriterion("end_pre not in", values, "endPre");
            return (Criteria) this;
        }

        public Criteria andEndPreBetween(Date value1, Date value2) {
            addCriterion("end_pre between", value1, value2, "endPre");
            return (Criteria) this;
        }

        public Criteria andEndPreNotBetween(Date value1, Date value2) {
            addCriterion("end_pre not between", value1, value2, "endPre");
            return (Criteria) this;
        }

        public Criteria andStartActIsNull() {
            addCriterion("start_act is null");
            return (Criteria) this;
        }

        public Criteria andStartActIsNotNull() {
            addCriterion("start_act is not null");
            return (Criteria) this;
        }

        public Criteria andStartActEqualTo(Date value) {
            addCriterion("start_act =", value, "startAct");
            return (Criteria) this;
        }

        public Criteria andStartActNotEqualTo(Date value) {
            addCriterion("start_act <>", value, "startAct");
            return (Criteria) this;
        }

        public Criteria andStartActGreaterThan(Date value) {
            addCriterion("start_act >", value, "startAct");
            return (Criteria) this;
        }

        public Criteria andStartActGreaterThanOrEqualTo(Date value) {
            addCriterion("start_act >=", value, "startAct");
            return (Criteria) this;
        }

        public Criteria andStartActLessThan(Date value) {
            addCriterion("start_act <", value, "startAct");
            return (Criteria) this;
        }

        public Criteria andStartActLessThanOrEqualTo(Date value) {
            addCriterion("start_act <=", value, "startAct");
            return (Criteria) this;
        }

        public Criteria andStartActIn(List<Date> values) {
            addCriterion("start_act in", values, "startAct");
            return (Criteria) this;
        }

        public Criteria andStartActNotIn(List<Date> values) {
            addCriterion("start_act not in", values, "startAct");
            return (Criteria) this;
        }

        public Criteria andStartActBetween(Date value1, Date value2) {
            addCriterion("start_act between", value1, value2, "startAct");
            return (Criteria) this;
        }

        public Criteria andStartActNotBetween(Date value1, Date value2) {
            addCriterion("start_act not between", value1, value2, "startAct");
            return (Criteria) this;
        }

        public Criteria andEndActIsNull() {
            addCriterion("end_act is null");
            return (Criteria) this;
        }

        public Criteria andEndActIsNotNull() {
            addCriterion("end_act is not null");
            return (Criteria) this;
        }

        public Criteria andEndActEqualTo(Date value) {
            addCriterion("end_act =", value, "endAct");
            return (Criteria) this;
        }

        public Criteria andEndActNotEqualTo(Date value) {
            addCriterion("end_act <>", value, "endAct");
            return (Criteria) this;
        }

        public Criteria andEndActGreaterThan(Date value) {
            addCriterion("end_act >", value, "endAct");
            return (Criteria) this;
        }

        public Criteria andEndActGreaterThanOrEqualTo(Date value) {
            addCriterion("end_act >=", value, "endAct");
            return (Criteria) this;
        }

        public Criteria andEndActLessThan(Date value) {
            addCriterion("end_act <", value, "endAct");
            return (Criteria) this;
        }

        public Criteria andEndActLessThanOrEqualTo(Date value) {
            addCriterion("end_act <=", value, "endAct");
            return (Criteria) this;
        }

        public Criteria andEndActIn(List<Date> values) {
            addCriterion("end_act in", values, "endAct");
            return (Criteria) this;
        }

        public Criteria andEndActNotIn(List<Date> values) {
            addCriterion("end_act not in", values, "endAct");
            return (Criteria) this;
        }

        public Criteria andEndActBetween(Date value1, Date value2) {
            addCriterion("end_act between", value1, value2, "endAct");
            return (Criteria) this;
        }

        public Criteria andEndActNotBetween(Date value1, Date value2) {
            addCriterion("end_act not between", value1, value2, "endAct");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNull() {
            addCriterion("add_time is null");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNotNull() {
            addCriterion("add_time is not null");
            return (Criteria) this;
        }

        public Criteria andAddTimeEqualTo(Date value) {
            addCriterion("add_time =", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualTo(Date value) {
            addCriterion("add_time <>", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThan(Date value) {
            addCriterion("add_time >", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("add_time >=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThan(Date value) {
            addCriterion("add_time <", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualTo(Date value) {
            addCriterion("add_time <=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeIn(List<Date> values) {
            addCriterion("add_time in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotIn(List<Date> values) {
            addCriterion("add_time not in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeBetween(Date value1, Date value2) {
            addCriterion("add_time between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotBetween(Date value1, Date value2) {
            addCriterion("add_time not between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddUserIsNull() {
            addCriterion("add_user is null");
            return (Criteria) this;
        }

        public Criteria andAddUserIsNotNull() {
            addCriterion("add_user is not null");
            return (Criteria) this;
        }

        public Criteria andAddUserEqualTo(Integer value) {
            addCriterion("add_user =", value, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserNotEqualTo(Integer value) {
            addCriterion("add_user <>", value, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserGreaterThan(Integer value) {
            addCriterion("add_user >", value, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("add_user >=", value, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserLessThan(Integer value) {
            addCriterion("add_user <", value, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserLessThanOrEqualTo(Integer value) {
            addCriterion("add_user <=", value, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserIn(List<Integer> values) {
            addCriterion("add_user in", values, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserNotIn(List<Integer> values) {
            addCriterion("add_user not in", values, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserBetween(Integer value1, Integer value2) {
            addCriterion("add_user between", value1, value2, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserNotBetween(Integer value1, Integer value2) {
            addCriterion("add_user not between", value1, value2, "addUser");
            return (Criteria) this;
        }

        public Criteria andModTimeIsNull() {
            addCriterion("mod_time is null");
            return (Criteria) this;
        }

        public Criteria andModTimeIsNotNull() {
            addCriterion("mod_time is not null");
            return (Criteria) this;
        }

        public Criteria andModTimeEqualTo(Date value) {
            addCriterion("mod_time =", value, "modTime");
            return (Criteria) this;
        }

        public Criteria andModTimeNotEqualTo(Date value) {
            addCriterion("mod_time <>", value, "modTime");
            return (Criteria) this;
        }

        public Criteria andModTimeGreaterThan(Date value) {
            addCriterion("mod_time >", value, "modTime");
            return (Criteria) this;
        }

        public Criteria andModTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("mod_time >=", value, "modTime");
            return (Criteria) this;
        }

        public Criteria andModTimeLessThan(Date value) {
            addCriterion("mod_time <", value, "modTime");
            return (Criteria) this;
        }

        public Criteria andModTimeLessThanOrEqualTo(Date value) {
            addCriterion("mod_time <=", value, "modTime");
            return (Criteria) this;
        }

        public Criteria andModTimeIn(List<Date> values) {
            addCriterion("mod_time in", values, "modTime");
            return (Criteria) this;
        }

        public Criteria andModTimeNotIn(List<Date> values) {
            addCriterion("mod_time not in", values, "modTime");
            return (Criteria) this;
        }

        public Criteria andModTimeBetween(Date value1, Date value2) {
            addCriterion("mod_time between", value1, value2, "modTime");
            return (Criteria) this;
        }

        public Criteria andModTimeNotBetween(Date value1, Date value2) {
            addCriterion("mod_time not between", value1, value2, "modTime");
            return (Criteria) this;
        }

        public Criteria andModUserIsNull() {
            addCriterion("mod_user is null");
            return (Criteria) this;
        }

        public Criteria andModUserIsNotNull() {
            addCriterion("mod_user is not null");
            return (Criteria) this;
        }

        public Criteria andModUserEqualTo(Integer value) {
            addCriterion("mod_user =", value, "modUser");
            return (Criteria) this;
        }

        public Criteria andModUserNotEqualTo(Integer value) {
            addCriterion("mod_user <>", value, "modUser");
            return (Criteria) this;
        }

        public Criteria andModUserGreaterThan(Integer value) {
            addCriterion("mod_user >", value, "modUser");
            return (Criteria) this;
        }

        public Criteria andModUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("mod_user >=", value, "modUser");
            return (Criteria) this;
        }

        public Criteria andModUserLessThan(Integer value) {
            addCriterion("mod_user <", value, "modUser");
            return (Criteria) this;
        }

        public Criteria andModUserLessThanOrEqualTo(Integer value) {
            addCriterion("mod_user <=", value, "modUser");
            return (Criteria) this;
        }

        public Criteria andModUserIn(List<Integer> values) {
            addCriterion("mod_user in", values, "modUser");
            return (Criteria) this;
        }

        public Criteria andModUserNotIn(List<Integer> values) {
            addCriterion("mod_user not in", values, "modUser");
            return (Criteria) this;
        }

        public Criteria andModUserBetween(Integer value1, Integer value2) {
            addCriterion("mod_user between", value1, value2, "modUser");
            return (Criteria) this;
        }

        public Criteria andModUserNotBetween(Integer value1, Integer value2) {
            addCriterion("mod_user not between", value1, value2, "modUser");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}