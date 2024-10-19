package com.dao;

import com.entity.TushuCollectionEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.view.TushuCollectionView;

/**
 * 图书收藏 Dao 接口
 *
 * @author 
 */
public interface TushuCollectionDao extends BaseMapper<TushuCollectionEntity> {

   List<TushuCollectionView> selectListView(Pagination page,@Param("params")Map<String,Object> params);

}
