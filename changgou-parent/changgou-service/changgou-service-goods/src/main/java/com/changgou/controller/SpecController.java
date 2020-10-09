package com.changgou.controller;

import com.changgou.goods.pojo.Spec;
import com.changgou.service.SpecService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/spec")
public class SpecController {
    @Autowired
    private SpecService specService;

    /***
     * 查询所有Spec
     * @return
     */
    @GetMapping
    public Result<Spec> selectAll() {
        List<Spec> lists = specService.findAll();
        return new Result<Spec>(true, StatusCode.OK, "查询所有成功！", lists);
    }

    /**
     * 根据ID查询Spec
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public Result<Spec> selectById(@PathVariable(value = "id") Integer id) {
        Spec spec = specService.findById(id);
        return new Result<Spec>(true, StatusCode.OK, "根据Id查询成功!", spec);
    }

    /***
     * 新增Spec
     * @param spec
     */
    @PostMapping
    public Result add(@RequestBody Spec spec) {
        specService.add(spec);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /***
     * 修改Spec数据
     * @param spec
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Spec spec, @PathVariable(value = "id") Integer id) {
        //设置主键
        spec.setId(id);
        specService.update(spec);
        return new Result(true, StatusCode.OK, "修改成功！");
    }


    /***
     * 删除Spec
     * @param id
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable(value = "id") Integer id) {
        specService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功！");
    }

    /***
     * Spec多条件搜索方法
     * @param spec
     * @return
     */

    @PostMapping(value = "/search")
    public Result<List<Spec>> findList(@RequestBody(required = false) Spec spec) {
        List<Spec> specs = specService.findList(spec);
        return new Result<List<Spec>>(true, StatusCode.OK, "多条件查询成功！", specs);
    }

    /*
     * 分页实现 PageHelper.startPage(page, size);后面的查询紧跟集合查询
     * 1.当前页
     * 2.每页显示多少条
     * */
    @GetMapping(value = "/searc/{page}/{size}")
    public Result<PageInfo<Spec>> findPage(@PathVariable(value = "page") Integer page, @PathVariable(value = "size") Integer size) {
        PageInfo<Spec> specPageInfo = specService.findPage(page, size);
        return new Result<PageInfo<Spec>>(true, StatusCode.OK, "分页查询成功！", specPageInfo);
    }

    /***
     * Spec多条件分页查询
     * @param spec
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}")
    public Result<PageInfo<Spec>> findPage(@RequestBody(required = false) Spec spec, @PathVariable(value = "page") Integer page, @PathVariable(value = "size") Integer size) {
        PageInfo<Spec> specPageInfo = specService.findPage(spec, page, size);
        return new Result<PageInfo<Spec>>(true, StatusCode.OK, "分页条件查询成功！", specPageInfo);
    }
}
