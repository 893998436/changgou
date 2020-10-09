package com.changgou.service.impl;

import com.changgou.dao.ParaMapper;
import com.changgou.goods.pojo.Para;
import com.changgou.service.ParaService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;


import java.util.List;

@Service
public class ParaServiceImpl implements ParaService {
    @Autowired
    private ParaMapper paraMapper;
    @Override
    public PageInfo<Para> findPage(Para para, int page, int size) {
        PageHelper.startPage(page,size);
        Example example=createExample(para);
        List<Para> paras = paraMapper.selectByExample(example);
        return new PageInfo<Para>(paras);
    }

    @Override
    public PageInfo<Para> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        List<Para> paras = paraMapper.selectAll();
        return new PageInfo<Para>(paras);
    }

    @Override
    public List<Para> findList(Para para) {
        Example example=createExample(para);
        return paraMapper.selectByExample(example);
    }
    public Example createExample(Para para){
        Example example=new Example(Para.class);
        Example.Criteria criteria = example.createCriteria();
        if(para!=null){
            // id
            if(!StringUtils.isEmpty(para.getId())){
                criteria.andEqualTo("id",para.getId());
            }
            // 名称
            if(!StringUtils.isEmpty(para.getName())){
                criteria.andLike("name","%"+para.getName()+"%");
            }
            // 选项
            if(!StringUtils.isEmpty(para.getOptions())){
                criteria.andEqualTo("options",para.getOptions());
            }
            // 排序
            if(!StringUtils.isEmpty(para.getSeq())){
                criteria.andEqualTo("seq",para.getSeq());
            }
            // 模板ID
            if(!StringUtils.isEmpty(para.getTemplateId())){
                criteria.andEqualTo("templateId",para.getTemplateId());
            }
        }
        return example;
    }
    /***
     * 删除Para
     * @param id
     */
    @Override
    public void delete(Integer id) {
    paraMapper.deleteByPrimaryKey(id);
    }
    /***
     * 修改Para数据
     * @param para
     */
    @Override
    public void update(Para para) {
    paraMapper.updateByPrimaryKey(para);
    }
    /***
     * 新增Para
     * @param para
     */
    @Override
    public void add(Para para) {
    paraMapper.insert(para);
    }

    @Override
    public Para findById(Integer id) {
        return paraMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Para> findAll() {
        return paraMapper.selectAll();
    }
}
