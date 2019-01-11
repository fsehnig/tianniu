package com.tianniu.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemberGoodsExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table MEMBER_GOODS
     *
     * @mbggenerated Wed Jan 09 15:34:13 CST 2019
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table MEMBER_GOODS
     *
     * @mbggenerated Wed Jan 09 15:34:13 CST 2019
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table MEMBER_GOODS
     *
     * @mbggenerated Wed Jan 09 15:34:13 CST 2019
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MEMBER_GOODS
     *
     * @mbggenerated Wed Jan 09 15:34:13 CST 2019
     */
    public MemberGoodsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MEMBER_GOODS
     *
     * @mbggenerated Wed Jan 09 15:34:13 CST 2019
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MEMBER_GOODS
     *
     * @mbggenerated Wed Jan 09 15:34:13 CST 2019
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MEMBER_GOODS
     *
     * @mbggenerated Wed Jan 09 15:34:13 CST 2019
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MEMBER_GOODS
     *
     * @mbggenerated Wed Jan 09 15:34:13 CST 2019
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MEMBER_GOODS
     *
     * @mbggenerated Wed Jan 09 15:34:13 CST 2019
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MEMBER_GOODS
     *
     * @mbggenerated Wed Jan 09 15:34:13 CST 2019
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MEMBER_GOODS
     *
     * @mbggenerated Wed Jan 09 15:34:13 CST 2019
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MEMBER_GOODS
     *
     * @mbggenerated Wed Jan 09 15:34:13 CST 2019
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MEMBER_GOODS
     *
     * @mbggenerated Wed Jan 09 15:34:13 CST 2019
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MEMBER_GOODS
     *
     * @mbggenerated Wed Jan 09 15:34:13 CST 2019
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table MEMBER_GOODS
     *
     * @mbggenerated Wed Jan 09 15:34:13 CST 2019
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
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

        public Criteria andGoodidIsNull() {
            addCriterion("GOODID is null");
            return (Criteria) this;
        }

        public Criteria andGoodidIsNotNull() {
            addCriterion("GOODID is not null");
            return (Criteria) this;
        }

        public Criteria andGoodidEqualTo(String value) {
            addCriterion("GOODID =", value, "goodid");
            return (Criteria) this;
        }

        public Criteria andGoodidNotEqualTo(String value) {
            addCriterion("GOODID <>", value, "goodid");
            return (Criteria) this;
        }

        public Criteria andGoodidGreaterThan(String value) {
            addCriterion("GOODID >", value, "goodid");
            return (Criteria) this;
        }

        public Criteria andGoodidGreaterThanOrEqualTo(String value) {
            addCriterion("GOODID >=", value, "goodid");
            return (Criteria) this;
        }

        public Criteria andGoodidLessThan(String value) {
            addCriterion("GOODID <", value, "goodid");
            return (Criteria) this;
        }

        public Criteria andGoodidLessThanOrEqualTo(String value) {
            addCriterion("GOODID <=", value, "goodid");
            return (Criteria) this;
        }

        public Criteria andGoodidLike(String value) {
            addCriterion("GOODID like", value, "goodid");
            return (Criteria) this;
        }

        public Criteria andGoodidNotLike(String value) {
            addCriterion("GOODID not like", value, "goodid");
            return (Criteria) this;
        }

        public Criteria andGoodidIn(List<String> values) {
            addCriterion("GOODID in", values, "goodid");
            return (Criteria) this;
        }

        public Criteria andGoodidNotIn(List<String> values) {
            addCriterion("GOODID not in", values, "goodid");
            return (Criteria) this;
        }

        public Criteria andGoodidBetween(String value1, String value2) {
            addCriterion("GOODID between", value1, value2, "goodid");
            return (Criteria) this;
        }

        public Criteria andGoodidNotBetween(String value1, String value2) {
            addCriterion("GOODID not between", value1, value2, "goodid");
            return (Criteria) this;
        }

        public Criteria andGoodproductidIsNull() {
            addCriterion("GOODPRODUCTID is null");
            return (Criteria) this;
        }

        public Criteria andGoodproductidIsNotNull() {
            addCriterion("GOODPRODUCTID is not null");
            return (Criteria) this;
        }

        public Criteria andGoodproductidEqualTo(String value) {
            addCriterion("GOODPRODUCTID =", value, "goodproductid");
            return (Criteria) this;
        }

        public Criteria andGoodproductidNotEqualTo(String value) {
            addCriterion("GOODPRODUCTID <>", value, "goodproductid");
            return (Criteria) this;
        }

        public Criteria andGoodproductidGreaterThan(String value) {
            addCriterion("GOODPRODUCTID >", value, "goodproductid");
            return (Criteria) this;
        }

        public Criteria andGoodproductidGreaterThanOrEqualTo(String value) {
            addCriterion("GOODPRODUCTID >=", value, "goodproductid");
            return (Criteria) this;
        }

        public Criteria andGoodproductidLessThan(String value) {
            addCriterion("GOODPRODUCTID <", value, "goodproductid");
            return (Criteria) this;
        }

        public Criteria andGoodproductidLessThanOrEqualTo(String value) {
            addCriterion("GOODPRODUCTID <=", value, "goodproductid");
            return (Criteria) this;
        }

        public Criteria andGoodproductidLike(String value) {
            addCriterion("GOODPRODUCTID like", value, "goodproductid");
            return (Criteria) this;
        }

        public Criteria andGoodproductidNotLike(String value) {
            addCriterion("GOODPRODUCTID not like", value, "goodproductid");
            return (Criteria) this;
        }

        public Criteria andGoodproductidIn(List<String> values) {
            addCriterion("GOODPRODUCTID in", values, "goodproductid");
            return (Criteria) this;
        }

        public Criteria andGoodproductidNotIn(List<String> values) {
            addCriterion("GOODPRODUCTID not in", values, "goodproductid");
            return (Criteria) this;
        }

        public Criteria andGoodproductidBetween(String value1, String value2) {
            addCriterion("GOODPRODUCTID between", value1, value2, "goodproductid");
            return (Criteria) this;
        }

        public Criteria andGoodproductidNotBetween(String value1, String value2) {
            addCriterion("GOODPRODUCTID not between", value1, value2, "goodproductid");
            return (Criteria) this;
        }

        public Criteria andGoodnameIsNull() {
            addCriterion("GOODNAME is null");
            return (Criteria) this;
        }

        public Criteria andGoodnameIsNotNull() {
            addCriterion("GOODNAME is not null");
            return (Criteria) this;
        }

        public Criteria andGoodnameEqualTo(String value) {
            addCriterion("GOODNAME =", value, "goodname");
            return (Criteria) this;
        }

        public Criteria andGoodnameNotEqualTo(String value) {
            addCriterion("GOODNAME <>", value, "goodname");
            return (Criteria) this;
        }

        public Criteria andGoodnameGreaterThan(String value) {
            addCriterion("GOODNAME >", value, "goodname");
            return (Criteria) this;
        }

        public Criteria andGoodnameGreaterThanOrEqualTo(String value) {
            addCriterion("GOODNAME >=", value, "goodname");
            return (Criteria) this;
        }

        public Criteria andGoodnameLessThan(String value) {
            addCriterion("GOODNAME <", value, "goodname");
            return (Criteria) this;
        }

        public Criteria andGoodnameLessThanOrEqualTo(String value) {
            addCriterion("GOODNAME <=", value, "goodname");
            return (Criteria) this;
        }

        public Criteria andGoodnameLike(String value) {
            addCriterion("GOODNAME like", value, "goodname");
            return (Criteria) this;
        }

        public Criteria andGoodnameNotLike(String value) {
            addCriterion("GOODNAME not like", value, "goodname");
            return (Criteria) this;
        }

        public Criteria andGoodnameIn(List<String> values) {
            addCriterion("GOODNAME in", values, "goodname");
            return (Criteria) this;
        }

        public Criteria andGoodnameNotIn(List<String> values) {
            addCriterion("GOODNAME not in", values, "goodname");
            return (Criteria) this;
        }

        public Criteria andGoodnameBetween(String value1, String value2) {
            addCriterion("GOODNAME between", value1, value2, "goodname");
            return (Criteria) this;
        }

        public Criteria andGoodnameNotBetween(String value1, String value2) {
            addCriterion("GOODNAME not between", value1, value2, "goodname");
            return (Criteria) this;
        }

        public Criteria andGoodreferredIsNull() {
            addCriterion("GOODREFERRED is null");
            return (Criteria) this;
        }

        public Criteria andGoodreferredIsNotNull() {
            addCriterion("GOODREFERRED is not null");
            return (Criteria) this;
        }

        public Criteria andGoodreferredEqualTo(String value) {
            addCriterion("GOODREFERRED =", value, "goodreferred");
            return (Criteria) this;
        }

        public Criteria andGoodreferredNotEqualTo(String value) {
            addCriterion("GOODREFERRED <>", value, "goodreferred");
            return (Criteria) this;
        }

        public Criteria andGoodreferredGreaterThan(String value) {
            addCriterion("GOODREFERRED >", value, "goodreferred");
            return (Criteria) this;
        }

        public Criteria andGoodreferredGreaterThanOrEqualTo(String value) {
            addCriterion("GOODREFERRED >=", value, "goodreferred");
            return (Criteria) this;
        }

        public Criteria andGoodreferredLessThan(String value) {
            addCriterion("GOODREFERRED <", value, "goodreferred");
            return (Criteria) this;
        }

        public Criteria andGoodreferredLessThanOrEqualTo(String value) {
            addCriterion("GOODREFERRED <=", value, "goodreferred");
            return (Criteria) this;
        }

        public Criteria andGoodreferredLike(String value) {
            addCriterion("GOODREFERRED like", value, "goodreferred");
            return (Criteria) this;
        }

        public Criteria andGoodreferredNotLike(String value) {
            addCriterion("GOODREFERRED not like", value, "goodreferred");
            return (Criteria) this;
        }

        public Criteria andGoodreferredIn(List<String> values) {
            addCriterion("GOODREFERRED in", values, "goodreferred");
            return (Criteria) this;
        }

        public Criteria andGoodreferredNotIn(List<String> values) {
            addCriterion("GOODREFERRED not in", values, "goodreferred");
            return (Criteria) this;
        }

        public Criteria andGoodreferredBetween(String value1, String value2) {
            addCriterion("GOODREFERRED between", value1, value2, "goodreferred");
            return (Criteria) this;
        }

        public Criteria andGoodreferredNotBetween(String value1, String value2) {
            addCriterion("GOODREFERRED not between", value1, value2, "goodreferred");
            return (Criteria) this;
        }

        public Criteria andGoodnumberIsNull() {
            addCriterion("GOODNUMBER is null");
            return (Criteria) this;
        }

        public Criteria andGoodnumberIsNotNull() {
            addCriterion("GOODNUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andGoodnumberEqualTo(String value) {
            addCriterion("GOODNUMBER =", value, "goodnumber");
            return (Criteria) this;
        }

        public Criteria andGoodnumberNotEqualTo(String value) {
            addCriterion("GOODNUMBER <>", value, "goodnumber");
            return (Criteria) this;
        }

        public Criteria andGoodnumberGreaterThan(String value) {
            addCriterion("GOODNUMBER >", value, "goodnumber");
            return (Criteria) this;
        }

        public Criteria andGoodnumberGreaterThanOrEqualTo(String value) {
            addCriterion("GOODNUMBER >=", value, "goodnumber");
            return (Criteria) this;
        }

        public Criteria andGoodnumberLessThan(String value) {
            addCriterion("GOODNUMBER <", value, "goodnumber");
            return (Criteria) this;
        }

        public Criteria andGoodnumberLessThanOrEqualTo(String value) {
            addCriterion("GOODNUMBER <=", value, "goodnumber");
            return (Criteria) this;
        }

        public Criteria andGoodnumberLike(String value) {
            addCriterion("GOODNUMBER like", value, "goodnumber");
            return (Criteria) this;
        }

        public Criteria andGoodnumberNotLike(String value) {
            addCriterion("GOODNUMBER not like", value, "goodnumber");
            return (Criteria) this;
        }

        public Criteria andGoodnumberIn(List<String> values) {
            addCriterion("GOODNUMBER in", values, "goodnumber");
            return (Criteria) this;
        }

        public Criteria andGoodnumberNotIn(List<String> values) {
            addCriterion("GOODNUMBER not in", values, "goodnumber");
            return (Criteria) this;
        }

        public Criteria andGoodnumberBetween(String value1, String value2) {
            addCriterion("GOODNUMBER between", value1, value2, "goodnumber");
            return (Criteria) this;
        }

        public Criteria andGoodnumberNotBetween(String value1, String value2) {
            addCriterion("GOODNUMBER not between", value1, value2, "goodnumber");
            return (Criteria) this;
        }

        public Criteria andGoodmoneyIsNull() {
            addCriterion("GOODMONEY is null");
            return (Criteria) this;
        }

        public Criteria andGoodmoneyIsNotNull() {
            addCriterion("GOODMONEY is not null");
            return (Criteria) this;
        }

        public Criteria andGoodmoneyEqualTo(String value) {
            addCriterion("GOODMONEY =", value, "goodmoney");
            return (Criteria) this;
        }

        public Criteria andGoodmoneyNotEqualTo(String value) {
            addCriterion("GOODMONEY <>", value, "goodmoney");
            return (Criteria) this;
        }

        public Criteria andGoodmoneyGreaterThan(String value) {
            addCriterion("GOODMONEY >", value, "goodmoney");
            return (Criteria) this;
        }

        public Criteria andGoodmoneyGreaterThanOrEqualTo(String value) {
            addCriterion("GOODMONEY >=", value, "goodmoney");
            return (Criteria) this;
        }

        public Criteria andGoodmoneyLessThan(String value) {
            addCriterion("GOODMONEY <", value, "goodmoney");
            return (Criteria) this;
        }

        public Criteria andGoodmoneyLessThanOrEqualTo(String value) {
            addCriterion("GOODMONEY <=", value, "goodmoney");
            return (Criteria) this;
        }

        public Criteria andGoodmoneyLike(String value) {
            addCriterion("GOODMONEY like", value, "goodmoney");
            return (Criteria) this;
        }

        public Criteria andGoodmoneyNotLike(String value) {
            addCriterion("GOODMONEY not like", value, "goodmoney");
            return (Criteria) this;
        }

        public Criteria andGoodmoneyIn(List<String> values) {
            addCriterion("GOODMONEY in", values, "goodmoney");
            return (Criteria) this;
        }

        public Criteria andGoodmoneyNotIn(List<String> values) {
            addCriterion("GOODMONEY not in", values, "goodmoney");
            return (Criteria) this;
        }

        public Criteria andGoodmoneyBetween(String value1, String value2) {
            addCriterion("GOODMONEY between", value1, value2, "goodmoney");
            return (Criteria) this;
        }

        public Criteria andGoodmoneyNotBetween(String value1, String value2) {
            addCriterion("GOODMONEY not between", value1, value2, "goodmoney");
            return (Criteria) this;
        }

        public Criteria andGoodscoreIsNull() {
            addCriterion("GOODSCORE is null");
            return (Criteria) this;
        }

        public Criteria andGoodscoreIsNotNull() {
            addCriterion("GOODSCORE is not null");
            return (Criteria) this;
        }

        public Criteria andGoodscoreEqualTo(String value) {
            addCriterion("GOODSCORE =", value, "goodscore");
            return (Criteria) this;
        }

        public Criteria andGoodscoreNotEqualTo(String value) {
            addCriterion("GOODSCORE <>", value, "goodscore");
            return (Criteria) this;
        }

        public Criteria andGoodscoreGreaterThan(String value) {
            addCriterion("GOODSCORE >", value, "goodscore");
            return (Criteria) this;
        }

        public Criteria andGoodscoreGreaterThanOrEqualTo(String value) {
            addCriterion("GOODSCORE >=", value, "goodscore");
            return (Criteria) this;
        }

        public Criteria andGoodscoreLessThan(String value) {
            addCriterion("GOODSCORE <", value, "goodscore");
            return (Criteria) this;
        }

        public Criteria andGoodscoreLessThanOrEqualTo(String value) {
            addCriterion("GOODSCORE <=", value, "goodscore");
            return (Criteria) this;
        }

        public Criteria andGoodscoreLike(String value) {
            addCriterion("GOODSCORE like", value, "goodscore");
            return (Criteria) this;
        }

        public Criteria andGoodscoreNotLike(String value) {
            addCriterion("GOODSCORE not like", value, "goodscore");
            return (Criteria) this;
        }

        public Criteria andGoodscoreIn(List<String> values) {
            addCriterion("GOODSCORE in", values, "goodscore");
            return (Criteria) this;
        }

        public Criteria andGoodscoreNotIn(List<String> values) {
            addCriterion("GOODSCORE not in", values, "goodscore");
            return (Criteria) this;
        }

        public Criteria andGoodscoreBetween(String value1, String value2) {
            addCriterion("GOODSCORE between", value1, value2, "goodscore");
            return (Criteria) this;
        }

        public Criteria andGoodscoreNotBetween(String value1, String value2) {
            addCriterion("GOODSCORE not between", value1, value2, "goodscore");
            return (Criteria) this;
        }

        public Criteria andGoodbegintimeIsNull() {
            addCriterion("GOODBEGINTIME is null");
            return (Criteria) this;
        }

        public Criteria andGoodbegintimeIsNotNull() {
            addCriterion("GOODBEGINTIME is not null");
            return (Criteria) this;
        }

        public Criteria andGoodbegintimeEqualTo(Date value) {
            addCriterion("GOODBEGINTIME =", value, "goodbegintime");
            return (Criteria) this;
        }

        public Criteria andGoodbegintimeNotEqualTo(Date value) {
            addCriterion("GOODBEGINTIME <>", value, "goodbegintime");
            return (Criteria) this;
        }

        public Criteria andGoodbegintimeGreaterThan(Date value) {
            addCriterion("GOODBEGINTIME >", value, "goodbegintime");
            return (Criteria) this;
        }

        public Criteria andGoodbegintimeGreaterThanOrEqualTo(Date value) {
            addCriterion("GOODBEGINTIME >=", value, "goodbegintime");
            return (Criteria) this;
        }

        public Criteria andGoodbegintimeLessThan(Date value) {
            addCriterion("GOODBEGINTIME <", value, "goodbegintime");
            return (Criteria) this;
        }

        public Criteria andGoodbegintimeLessThanOrEqualTo(Date value) {
            addCriterion("GOODBEGINTIME <=", value, "goodbegintime");
            return (Criteria) this;
        }

        public Criteria andGoodbegintimeIn(List<Date> values) {
            addCriterion("GOODBEGINTIME in", values, "goodbegintime");
            return (Criteria) this;
        }

        public Criteria andGoodbegintimeNotIn(List<Date> values) {
            addCriterion("GOODBEGINTIME not in", values, "goodbegintime");
            return (Criteria) this;
        }

        public Criteria andGoodbegintimeBetween(Date value1, Date value2) {
            addCriterion("GOODBEGINTIME between", value1, value2, "goodbegintime");
            return (Criteria) this;
        }

        public Criteria andGoodbegintimeNotBetween(Date value1, Date value2) {
            addCriterion("GOODBEGINTIME not between", value1, value2, "goodbegintime");
            return (Criteria) this;
        }

        public Criteria andGoodendtimeIsNull() {
            addCriterion("GOODENDTIME is null");
            return (Criteria) this;
        }

        public Criteria andGoodendtimeIsNotNull() {
            addCriterion("GOODENDTIME is not null");
            return (Criteria) this;
        }

        public Criteria andGoodendtimeEqualTo(Date value) {
            addCriterion("GOODENDTIME =", value, "goodendtime");
            return (Criteria) this;
        }

        public Criteria andGoodendtimeNotEqualTo(Date value) {
            addCriterion("GOODENDTIME <>", value, "goodendtime");
            return (Criteria) this;
        }

        public Criteria andGoodendtimeGreaterThan(Date value) {
            addCriterion("GOODENDTIME >", value, "goodendtime");
            return (Criteria) this;
        }

        public Criteria andGoodendtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("GOODENDTIME >=", value, "goodendtime");
            return (Criteria) this;
        }

        public Criteria andGoodendtimeLessThan(Date value) {
            addCriterion("GOODENDTIME <", value, "goodendtime");
            return (Criteria) this;
        }

        public Criteria andGoodendtimeLessThanOrEqualTo(Date value) {
            addCriterion("GOODENDTIME <=", value, "goodendtime");
            return (Criteria) this;
        }

        public Criteria andGoodendtimeIn(List<Date> values) {
            addCriterion("GOODENDTIME in", values, "goodendtime");
            return (Criteria) this;
        }

        public Criteria andGoodendtimeNotIn(List<Date> values) {
            addCriterion("GOODENDTIME not in", values, "goodendtime");
            return (Criteria) this;
        }

        public Criteria andGoodendtimeBetween(Date value1, Date value2) {
            addCriterion("GOODENDTIME between", value1, value2, "goodendtime");
            return (Criteria) this;
        }

        public Criteria andGoodendtimeNotBetween(Date value1, Date value2) {
            addCriterion("GOODENDTIME not between", value1, value2, "goodendtime");
            return (Criteria) this;
        }

        public Criteria andGoodoverdueIsNull() {
            addCriterion("GOODOVERDUE is null");
            return (Criteria) this;
        }

        public Criteria andGoodoverdueIsNotNull() {
            addCriterion("GOODOVERDUE is not null");
            return (Criteria) this;
        }

        public Criteria andGoodoverdueEqualTo(String value) {
            addCriterion("GOODOVERDUE =", value, "goodoverdue");
            return (Criteria) this;
        }

        public Criteria andGoodoverdueNotEqualTo(String value) {
            addCriterion("GOODOVERDUE <>", value, "goodoverdue");
            return (Criteria) this;
        }

        public Criteria andGoodoverdueGreaterThan(String value) {
            addCriterion("GOODOVERDUE >", value, "goodoverdue");
            return (Criteria) this;
        }

        public Criteria andGoodoverdueGreaterThanOrEqualTo(String value) {
            addCriterion("GOODOVERDUE >=", value, "goodoverdue");
            return (Criteria) this;
        }

        public Criteria andGoodoverdueLessThan(String value) {
            addCriterion("GOODOVERDUE <", value, "goodoverdue");
            return (Criteria) this;
        }

        public Criteria andGoodoverdueLessThanOrEqualTo(String value) {
            addCriterion("GOODOVERDUE <=", value, "goodoverdue");
            return (Criteria) this;
        }

        public Criteria andGoodoverdueLike(String value) {
            addCriterion("GOODOVERDUE like", value, "goodoverdue");
            return (Criteria) this;
        }

        public Criteria andGoodoverdueNotLike(String value) {
            addCriterion("GOODOVERDUE not like", value, "goodoverdue");
            return (Criteria) this;
        }

        public Criteria andGoodoverdueIn(List<String> values) {
            addCriterion("GOODOVERDUE in", values, "goodoverdue");
            return (Criteria) this;
        }

        public Criteria andGoodoverdueNotIn(List<String> values) {
            addCriterion("GOODOVERDUE not in", values, "goodoverdue");
            return (Criteria) this;
        }

        public Criteria andGoodoverdueBetween(String value1, String value2) {
            addCriterion("GOODOVERDUE between", value1, value2, "goodoverdue");
            return (Criteria) this;
        }

        public Criteria andGoodoverdueNotBetween(String value1, String value2) {
            addCriterion("GOODOVERDUE not between", value1, value2, "goodoverdue");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table MEMBER_GOODS
     *
     * @mbggenerated do_not_delete_during_merge Wed Jan 09 15:34:13 CST 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table MEMBER_GOODS
     *
     * @mbggenerated Wed Jan 09 15:34:13 CST 2019
     */
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