package com.changgou.service.impl;

import com.changgou.dao.CategoryMapper;
import com.changgou.goods.pojo.Category;
import com.changgou.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public PageInfo<Category> findPage(Category category, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(category);
        List<Category> categories = categoryMapper.selectByExample(example);
        return new PageInfo<Category>(categories);
    }

    @Override
    public PageInfo<Category> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        List<Category> categories = categoryMapper.selectAll();
        return new PageInfo<Category>(categories);
    }

    /***
     * Category多条件搜索方法
     * @param category
     * @return
     */
    @Override
    public List<Category> findList(Category category) {
        //构造条件
        Example example = createExample(category);
        return categoryMapper.selectByExample(example);
    }

    public Example createExample(Category category) {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        if (category != null) {
            // 分类ID
            if (!StringUtils.isEmpty(category.getId())) {
                criteria.andEqualTo("id", category.getId());
            }
            // 分类名称
            if (!StringUtils.isEmpty(category.getName())) {
                criteria.andLike("name", "%" + category.getName() + "%");
            }
            // 商品数量
            if (!StringUtils.isEmpty(category.getGoodsNum())) {
                criteria.andEqualTo("goodsNum", category.getGoodsNum());
            }
            // 是否显示
            if (!StringUtils.isEmpty(category.getIsShow())) {
                criteria.andEqualTo("isShow", category.getIsShow());
            }
            // 是否导航
            if (!StringUtils.isEmpty(category.getIsMenu())) {
                criteria.andEqualTo("isMenu", category.getIsMenu());
            }
            // 排序
            if (!StringUtils.isEmpty(category.getSeq())) {
                criteria.andEqualTo("seq", category.getSeq());
            }
            //上级ID
            if (!StringUtils.isEmpty(category.getParentId())) {
                criteria.andEqualTo("parentId", category.getParentId());
            }
            //模板ID
            if (!StringUtils.isEmpty(category.getTemplateId())) {
                criteria.andEqualTo("templateId", category.getTemplateId());
            }
        }
        return example;
    }

    @Override
    public void delete(Integer id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }

    @Override
    public void add(Category category) {
        categoryMapper.insert(category);
    }

    @Override
    public Category findById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryMapper.selectAll();
    }

    /***
     * 根据父节点ID查询
     * @param pid:父节点ID
     */
    @Override
    public List<Category> findByParentId(Integer pid) {
        Category category = new Category();
        category.setParentId(pid);
        return categoryMapper.select(category);
    }
}
