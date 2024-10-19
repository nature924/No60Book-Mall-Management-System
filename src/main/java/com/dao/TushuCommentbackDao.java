package com.dao;

import com.entity.TushuCommentbackEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.view.TushuCommentbackView;

/**
 * 图书评价 Dao 接口
 *
 * @author 
 */
public interface TushuCommentbackDao extends BaseMapper<TushuCommentbackEntity> {

   List<TushuCommentbackView> selectListView(Pagination page,@Param("params")Map<String,Object> params);

}
