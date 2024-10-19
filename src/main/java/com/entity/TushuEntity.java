package com.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 图书
 *
 * @author 
 * @email
 */
@TableName("tushu")
public class TushuEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;


	public TushuEntity() {

	}

	public TushuEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @TableField(value = "id")

    private Integer id;


    /**
     * 图书名称
     */
    @TableField(value = "tushu_name")

    private String tushuName;


    /**
     * 图书照片
     */
    @TableField(value = "tushu_photo")

    private String tushuPhoto;


    /**
     * 图书类型
     */
    @TableField(value = "tushu_types")

    private Integer tushuTypes;


    /**
     * 图书库存
     */
    @TableField(value = "tushu_kucun_number")

    private Integer tushuKucunNumber;


    /**
     * 购买获得积分
     */
    @TableField(value = "tushu_price")

    private Integer tushuPrice;


    /**
     * 图书原价
     */
    @TableField(value = "tushu_old_money")

    private Double tushuOldMoney;


    /**
     * 现价
     */
    @TableField(value = "tushu_new_money")

    private Double tushuNewMoney;


    /**
     * 点击次数
     */
    @TableField(value = "tushu_clicknum")

    private Integer tushuClicknum;


    /**
     * 图书简介
     */
    @TableField(value = "tushu_content")

    private String tushuContent;


    /**
     * 是否上架
     */
    @TableField(value = "shangxia_types")

    private Integer shangxiaTypes;


    /**
     * 逻辑删除
     */
    @TableField(value = "tushu_delete")

    private Integer tushuDelete;


    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    @TableField(value = "create_time",fill = FieldFill.INSERT)

    private Date createTime;


    /**
	 * 设置：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 获取：主键
	 */

    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 设置：图书名称
	 */
    public String getTushuName() {
        return tushuName;
    }


    /**
	 * 获取：图书名称
	 */

    public void setTushuName(String tushuName) {
        this.tushuName = tushuName;
    }
    /**
	 * 设置：图书照片
	 */
    public String getTushuPhoto() {
        return tushuPhoto;
    }


    /**
	 * 获取：图书照片
	 */

    public void setTushuPhoto(String tushuPhoto) {
        this.tushuPhoto = tushuPhoto;
    }
    /**
	 * 设置：图书类型
	 */
    public Integer getTushuTypes() {
        return tushuTypes;
    }


    /**
	 * 获取：图书类型
	 */

    public void setTushuTypes(Integer tushuTypes) {
        this.tushuTypes = tushuTypes;
    }
    /**
	 * 设置：图书库存
	 */
    public Integer getTushuKucunNumber() {
        return tushuKucunNumber;
    }


    /**
	 * 获取：图书库存
	 */

    public void setTushuKucunNumber(Integer tushuKucunNumber) {
        this.tushuKucunNumber = tushuKucunNumber;
    }
    /**
	 * 设置：购买获得积分
	 */
    public Integer getTushuPrice() {
        return tushuPrice;
    }


    /**
	 * 获取：购买获得积分
	 */

    public void setTushuPrice(Integer tushuPrice) {
        this.tushuPrice = tushuPrice;
    }
    /**
	 * 设置：图书原价
	 */
    public Double getTushuOldMoney() {
        return tushuOldMoney;
    }


    /**
	 * 获取：图书原价
	 */

    public void setTushuOldMoney(Double tushuOldMoney) {
        this.tushuOldMoney = tushuOldMoney;
    }
    /**
	 * 设置：现价
	 */
    public Double getTushuNewMoney() {
        return tushuNewMoney;
    }


    /**
	 * 获取：现价
	 */

    public void setTushuNewMoney(Double tushuNewMoney) {
        this.tushuNewMoney = tushuNewMoney;
    }
    /**
	 * 设置：点击次数
	 */
    public Integer getTushuClicknum() {
        return tushuClicknum;
    }


    /**
	 * 获取：点击次数
	 */

    public void setTushuClicknum(Integer tushuClicknum) {
        this.tushuClicknum = tushuClicknum;
    }
    /**
	 * 设置：图书简介
	 */
    public String getTushuContent() {
        return tushuContent;
    }


    /**
	 * 获取：图书简介
	 */

    public void setTushuContent(String tushuContent) {
        this.tushuContent = tushuContent;
    }
    /**
	 * 设置：是否上架
	 */
    public Integer getShangxiaTypes() {
        return shangxiaTypes;
    }


    /**
	 * 获取：是否上架
	 */

    public void setShangxiaTypes(Integer shangxiaTypes) {
        this.shangxiaTypes = shangxiaTypes;
    }
    /**
	 * 设置：逻辑删除
	 */
    public Integer getTushuDelete() {
        return tushuDelete;
    }


    /**
	 * 获取：逻辑删除
	 */

    public void setTushuDelete(Integer tushuDelete) {
        this.tushuDelete = tushuDelete;
    }
    /**
	 * 设置：创建时间
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 获取：创建时间
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Tushu{" +
            "id=" + id +
            ", tushuName=" + tushuName +
            ", tushuPhoto=" + tushuPhoto +
            ", tushuTypes=" + tushuTypes +
            ", tushuKucunNumber=" + tushuKucunNumber +
            ", tushuPrice=" + tushuPrice +
            ", tushuOldMoney=" + tushuOldMoney +
            ", tushuNewMoney=" + tushuNewMoney +
            ", tushuClicknum=" + tushuClicknum +
            ", tushuContent=" + tushuContent +
            ", shangxiaTypes=" + shangxiaTypes +
            ", tushuDelete=" + tushuDelete +
            ", createTime=" + createTime +
        "}";
    }
}
