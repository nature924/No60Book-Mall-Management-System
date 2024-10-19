package com.entity.model;

import com.entity.TushuEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;


/**
 * 图书
 * 接收传参的实体类
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了）
 * 取自ModelAndView 的model名称
 */
public class TushuModel implements Serializable {
    private static final long serialVersionUID = 1L;




    /**
     * 主键
     */
    private Integer id;


    /**
     * 图书名称
     */
    private String tushuName;


    /**
     * 图书照片
     */
    private String tushuPhoto;


    /**
     * 图书类型
     */
    private Integer tushuTypes;


    /**
     * 图书库存
     */
    private Integer tushuKucunNumber;


    /**
     * 购买获得积分
     */
    private Integer tushuPrice;


    /**
     * 图书原价
     */
    private Double tushuOldMoney;


    /**
     * 现价
     */
    private Double tushuNewMoney;


    /**
     * 点击次数
     */
    private Integer tushuClicknum;


    /**
     * 图书简介
     */
    private String tushuContent;


    /**
     * 是否上架
     */
    private Integer shangxiaTypes;


    /**
     * 逻辑删除
     */
    private Integer tushuDelete;


    /**
     * 创建时间  show1 show2 photoShow
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date createTime;


    /**
	 * 获取：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 设置：主键
	 */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 获取：图书名称
	 */
    public String getTushuName() {
        return tushuName;
    }


    /**
	 * 设置：图书名称
	 */
    public void setTushuName(String tushuName) {
        this.tushuName = tushuName;
    }
    /**
	 * 获取：图书照片
	 */
    public String getTushuPhoto() {
        return tushuPhoto;
    }


    /**
	 * 设置：图书照片
	 */
    public void setTushuPhoto(String tushuPhoto) {
        this.tushuPhoto = tushuPhoto;
    }
    /**
	 * 获取：图书类型
	 */
    public Integer getTushuTypes() {
        return tushuTypes;
    }


    /**
	 * 设置：图书类型
	 */
    public void setTushuTypes(Integer tushuTypes) {
        this.tushuTypes = tushuTypes;
    }
    /**
	 * 获取：图书库存
	 */
    public Integer getTushuKucunNumber() {
        return tushuKucunNumber;
    }


    /**
	 * 设置：图书库存
	 */
    public void setTushuKucunNumber(Integer tushuKucunNumber) {
        this.tushuKucunNumber = tushuKucunNumber;
    }
    /**
	 * 获取：购买获得积分
	 */
    public Integer getTushuPrice() {
        return tushuPrice;
    }


    /**
	 * 设置：购买获得积分
	 */
    public void setTushuPrice(Integer tushuPrice) {
        this.tushuPrice = tushuPrice;
    }
    /**
	 * 获取：图书原价
	 */
    public Double getTushuOldMoney() {
        return tushuOldMoney;
    }


    /**
	 * 设置：图书原价
	 */
    public void setTushuOldMoney(Double tushuOldMoney) {
        this.tushuOldMoney = tushuOldMoney;
    }
    /**
	 * 获取：现价
	 */
    public Double getTushuNewMoney() {
        return tushuNewMoney;
    }


    /**
	 * 设置：现价
	 */
    public void setTushuNewMoney(Double tushuNewMoney) {
        this.tushuNewMoney = tushuNewMoney;
    }
    /**
	 * 获取：点击次数
	 */
    public Integer getTushuClicknum() {
        return tushuClicknum;
    }


    /**
	 * 设置：点击次数
	 */
    public void setTushuClicknum(Integer tushuClicknum) {
        this.tushuClicknum = tushuClicknum;
    }
    /**
	 * 获取：图书简介
	 */
    public String getTushuContent() {
        return tushuContent;
    }


    /**
	 * 设置：图书简介
	 */
    public void setTushuContent(String tushuContent) {
        this.tushuContent = tushuContent;
    }
    /**
	 * 获取：是否上架
	 */
    public Integer getShangxiaTypes() {
        return shangxiaTypes;
    }


    /**
	 * 设置：是否上架
	 */
    public void setShangxiaTypes(Integer shangxiaTypes) {
        this.shangxiaTypes = shangxiaTypes;
    }
    /**
	 * 获取：逻辑删除
	 */
    public Integer getTushuDelete() {
        return tushuDelete;
    }


    /**
	 * 设置：逻辑删除
	 */
    public void setTushuDelete(Integer tushuDelete) {
        this.tushuDelete = tushuDelete;
    }
    /**
	 * 获取：创建时间  show1 show2 photoShow
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 设置：创建时间  show1 show2 photoShow
	 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    }
