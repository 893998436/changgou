package com.changgou.service;

import com.changgou.goods.pojo.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BrandService {
    /*
    * 查询你所有
    * */
   List<Brand> findAll();

   /*
   *
   * 根据id查询
   * */
   Brand findById(Integer id);
   /*
   * 增加品牌
   * */
   void add(Brand brand);
   /*
   * 根据ID修改品牌数据
   * */
   void  update(Brand brand);
   /*
   * 根据ID删除品牌
   *
   * */
   void deleteById(Integer id);
   /*
   *根据品牌多条件搜索
   * */
   List<Brand> findList(Brand brand);
   /*
   * 带分页的条件搜索
   * page:当前页
   * size：每页显示的条数
   * */
   PageInfo<Brand> findPage(Integer page,Integer size);
    /*
     * 分页+条件的条件搜索
     * page:当前页
     * size：每页显示的条数
     * brand:搜索条件
     * */
    PageInfo<Brand> findPage(Brand brand,Integer page,Integer size);


}
