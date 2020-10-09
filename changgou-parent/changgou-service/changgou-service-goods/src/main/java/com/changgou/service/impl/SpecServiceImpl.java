package com.changgou.service.impl;

import com.changgou.dao.SpecMapper;
import com.changgou.goods.pojo.Spec;
import com.changgou.service.SpecService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SpecServiceImpl implements SpecService {
    @Autowired
    private SpecMapper specMapper;

    /***
     * Spec多条件分页查询
     * @param spec
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Spec> findPage(Spec spec, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        //条件
        Example example = createExample(spec);
        //查询
        List<Spec> specs = specMapper.selectByExample(example);
        return new PageInfo<Spec>(specs);
    }

    /***
     * Spec分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Spec> findPage(int page, int size) {
        /*
         * 分页实现 PageHelper.startPage(page, size);后面的查询紧跟集合查询
         * 1.当前页
         * 2.每页显示多少条
         * */
        //分页
        PageHelper.startPage(page, size);
        //查询
        List<Spec> specs = specMapper.selectAll();
        return new PageInfo<Spec>(specs);
    }

    /***
     * Spec多条件搜索方法
     * @param spec
     * @return
     */
    @Override
    public List<Spec> findList(Spec spec) {
        Example example = createExample(spec);
        //根据构建的条件查询数据
        return specMapper.selectByExample(example);
    }

    public Example createExample(Spec spec) {
        //自定义条件搜索对象 Example
        Example example = new Example(Spec.class);
        //条件构造器
        Example.Criteria criteria = example.createCriteria();
        if (spec != null) {
            // ID
            if (!StringUtils.isEmpty(spec.getId())) {
                criteria.andEqualTo("id", spec.getId());
            }
            // 名称
            if (!StringUtils.isEmpty(spec.getName())) {
                criteria.andLike("name", "%" + spec.getName() + "%");
            }
            // 规格选项
            if (!StringUtils.isEmpty(spec.getOptions())) {
                criteria.andEqualTo("options", spec.getOptions());
            }
            // 排序
            if (!StringUtils.isEmpty(spec.getSeq())) {
                criteria.andEqualTo("seq", spec.getSeq());
            }
            // 模板ID
            if (!StringUtils.isEmpty(spec.getTemplateId())) {
                criteria.andEqualTo("templateId", spec.getTemplateId());
            }
        }
        return example;
    }

    /***
     * 删除Spec
     * @param id
     */
    @Override
    public void delete(Integer id) {
        specMapper.deleteByPrimaryKey(id);
    }

    /***
     * 修改Spec数据
     * @param spec
     */
    @Override
    public void update(Spec spec) {
        specMapper.updateByPrimaryKey(spec);
    }

    /***
     * 新增Spec
     * @param spec
     */

    @Override
    public void add(Spec spec) {
        specMapper.insert(spec);
    }

    @Override
    public Spec findById(Integer id) {
        return specMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Spec> findAll() {
        return specMapper.selectAll();
    }
}
