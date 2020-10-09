package com.changgou.controller;


import com.changgou.goods.pojo.Template;
import com.changgou.service.TemplateService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/template")
public class TemplateController {
    @Autowired
    private TemplateService templateService;

    /***
     * 查询所有Template
     * @return
     */
    @GetMapping
    public Result<Template> findAll() {
        List<Template> lists = templateService.findAll();
        return new Result<Template>(true, StatusCode.OK, "查询全部成功！", lists);
    }

    /**
     * 根据ID查询Template
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public Result<Template> findById(@PathVariable(value = "id") Integer id) {
        Template template = templateService.findById(id);
        return new Result<Template>(true, StatusCode.OK, "查询成功!", template);
    }

    /***
     * 新增Template
     * @param template
     */
    @PostMapping
    public Result add(@RequestBody Template template) {
        templateService.add(template);
        return new Result(true, StatusCode.OK, "添加成功！");
    }

    /***
     * 修改Template数据
     * @param template
     */
    @PutMapping(value = "/{id}")
    public Result update(@PathVariable(value = "id") Integer id, @RequestBody Template template) {
        //设置id
        template.setId(id);
        templateService.update(template);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /***
     * 删除Template
     * @param id
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable(value = "id") Integer id) {
        templateService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功！");
    }

    /***
     * Template多条件搜索方法
     * @param template
     * @return
     */
    @PostMapping(value = "/search")
    public Result<List<Template>> findList(@RequestBody(required = false) Template template) {
        List<Template> lists = templateService.findList(template);
        return new Result<List<Template>>(true, StatusCode.OK, "条件查询成功!", lists);
    }

    /***
     * Template分页查询
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}")
    public Result<PageInfo<Template>> findPage(@PathVariable(value = "page") Integer page, @PathVariable(value = "size") Integer size) {
        PageInfo<Template> templatePageInfo=templateService.findPage(page,size);
        return new Result<PageInfo<Template>>(true,StatusCode.OK,"分页查询成功!",templatePageInfo);
    }
    /***
     * Template多条件分页查询
     * @param template
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}")
    public  Result<PageInfo<Template>> findPage(@RequestBody(required = false) Template template,@PathVariable(value = "page") Integer page,@PathVariable(value = "size") Integer size){
        PageInfo<Template> templatePageInfo=templateService.findPage(template,page,size);
        return  new Result<PageInfo<Template>>(true,StatusCode.OK,"分页条件查询成功！",templatePageInfo);
    }


}