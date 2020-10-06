package com.changgou.service.impl;

import com.changgou.dao.BrandMapper;
import com.changgou.goods.pojo.Brand;
import com.changgou.service.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    /*
     * 查询所有
     * */
    @Override
    public List<Brand> findAll() {
        //查询所有->Mapper.selectAll()
        return brandMapper.selectAll();
    }

    @Override
    public Brand findById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(Brand brand) {
        //使用mapper.insertSelective()实现增加
        //方法中带有Selective会忽略空值
        //没有的会空下
        brandMapper.insertSelective(brand);
    }

    /*
     * 使用通用mapper.updateByPrimaryKeySelective()
     * */
    @Override
    public void update(Brand brand) {

        brandMapper.updateByPrimaryKeySelective(brand);
    }

    /*
     * 根据ID删除 品牌
     * */
    @Override
    public void deleteById(Integer id) {
        brandMapper.deleteByPrimaryKey(id);

    }

    /*
     * 多条件查询
     * */
    @Override
    public List<Brand> findList(Brand brand) {
        Example example = createExample(brand);
        return brandMapper.selectByExample(example);
    }


    /*
     *条件查询抽取
     * */
    public Example createExample(Brand brand) {
        //自定义条件搜索对象 Example
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();//条件构造器
        if (brand != null) {
            //brand.name!=null 根据名字查询
            if (!StringUtils.isEmpty(brand.getName())) {
                /*
                 * 1.Brand的屬性名
                 * 2.占位符参数，搜索条件
                 * */
                criteria.andLike("name", "%" + brand.getName() + "%");
            }
            //brand.letter!=null 根据首字母查询and letter="H"
            if (!StringUtils.isEmpty(brand.getLetter())) {
                criteria.andEqualTo("letter", brand.getLetter());
            }
        }
        return example;
    }

    /*
     * 分页查询
     * */
    @Override
    public PageInfo<Brand> findPage(Integer page, Integer size) {
        /*
         * 分页实现 PageHelper.startPage(page, size);后面的查询紧跟集合查询
         * 1.当前页
         * 2.每页显示多少条
         * */
        PageHelper.startPage(page, size);
        List<Brand> brands=brandMapper.selectAll();
       //封装PageInfo<Brand>对象
        return new PageInfo<Brand> (brands);
    }

    @Override
    public PageInfo<Brand> findPage(Brand brand, Integer page, Integer size) {
        //分页
        PageHelper.startPage(page,size);
        //条件
        Example example=createExample(brand);
        List<Brand> brands= brandMapper.selectByExample(example);
        //封装PageInfo<Brand>
        return new PageInfo<Brand>(brands);
    }

}
