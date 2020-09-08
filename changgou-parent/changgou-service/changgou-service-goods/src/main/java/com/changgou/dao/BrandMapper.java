package com.changgou.dao;


import com.changgou.goods.pojo.Brand;
import tk.mybatis.mapper.common.Mapper;



/*
* dao使用通用mapper,dao接口需要继承Mapper
* 增加数据，调用Mapper.insert(
* 增加数据，调用Mapper.insertSelective(
* 修改数据，调用Mapper. update(T)
* 修改数据，调用Mapper. updateByPrimayKey(T)
* 查询数据，根据ID查询: Mapper.selectByPrimaryKey(IDl
* 查询数据，条件查询：Mapper.select(T)
* */
public interface BrandMapper extends Mapper<Brand> {

}
