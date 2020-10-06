package com.changgou.service.impl;

import com.changgou.dao.TemplateMapper;
import com.changgou.goods.pojo.Template;
import com.changgou.service.TemplateService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateMapper templateMapper;
    /***
     * Template多条件分页查询
     * @param template
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Template> findPage(Template template, int page, int size) {
        //分页
        PageHelper.startPage(page,size);
        //条件
        Example example=createExample(template);
        //查询
        List<Template> templates=templateMapper.selectByExample(example);
        return new PageInfo<Template>(templates);
    }
    /***
     * Template分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Template> findPage(int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        List<Template> templates = templateMapper.selectAll();
        return new  PageInfo<Template>(templates);
    }
    /***
     * Template多条件搜索方法
     * @param template
     * @return
     */
    @Override
    public List<Template> findList(Template template) {
        Example example =createExample(template);
        return templateMapper.selectByExample(example);
    }

    /*
    * 条件查询
    * */
    public Example createExample(Template template){
        //自定义条件搜索对象 Example
        Example example=new Example(Template.class);
        Example.Criteria criteria=example.createCriteria();
        if(template!=null){
            // ID
            if(!StringUtils.isEmpty(template.getId())){
                criteria.andEqualTo("id",template.getId());
            }
            // 模板名称
            if(!StringUtils.isEmpty(template.getName())){
                criteria.andLike("name","%"+template.getName()+"%");
            }
            // 规格数量
            if(!StringUtils.isEmpty(template.getSpecNum())){
                criteria.andEqualTo("specNum",template.getSpecNum());
            }
            // 参数数量
            if(!StringUtils.isEmpty(template.getParaNum())){
                criteria.andEqualTo("paraNum",template.getParaNum());
            }
        }
        return example;
        }


    /***
     * 删除Template
     * @param id
     */
    @Override
    public void delete(Integer id) {
        templateMapper.deleteByPrimaryKey(id);

    }
    /***
     * 修改Template数据
     * @param template
     */
    @Override
    public void update(Template template) {
    templateMapper.updateByPrimaryKey(template);
    }
    /***
     * 新增Template
     * @param template
     */
    @Override
    public void add(Template template) {
        templateMapper.insert(template);

    }
    /**
     * 根据ID查询Template
     * @param id
     * @return
     */
    @Override
    public Template findById(Integer id) {

        return templateMapper.selectByPrimaryKey(id);
    }

    /***
     * 查询所有Template
     * @return
     */
    @Override
    public List<Template> findAll() {
        return templateMapper.selectAll();
    }
}
