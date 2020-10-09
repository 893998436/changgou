package com.changgou.controller;

import com.changgou.goods.pojo.Para;
import com.changgou.service.ParaService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/para")
@RestController
@CrossOrigin
public class ParaController {
    @Autowired
    private ParaService paraService;

    /***
     * 查询所有Para
     * @return
     */
    @GetMapping
    public Result<Para> findAll() {
        List<Para> paras = paraService.findAll();
        return new Result<Para>(true, StatusCode.OK, "查询所有成功！", paras);
    }

    /**
     * 根据ID查询Para
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public Result<Para> findAll(@PathVariable(value = "id") Integer id) {
        Para para = paraService.findById(id);
        return new Result<Para>(true, StatusCode.OK, "根据ID查询成功！", para);
    }

    /***
     * 新增Para
     * @param para
     */
    @PostMapping
    public Result add(@RequestBody Para para) {
        paraService.add(para);
        return new Result(true, StatusCode.OK, "添加成功！");
    }
    /***
    * 修改Para数据
    * @param para
    */
    @PutMapping(value = "/{id}")
    public Result update(@PathVariable(value = "id")Integer id,@RequestBody Para para){
        para.setId(id);
        paraService.update(para);
        return new Result(true,StatusCode.OK,"修改成功!");
    }
    /***
     * 删除Para
     * @param id
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable(value = "id")Integer id){
        paraService.delete(id);
        return  new Result(true,StatusCode.OK,"删除成功！");
    }
    /***
     * Para多条件搜索方法
     * @param
     * @return
     */
    @PostMapping(value = "/search")
    public  Result<List<Para>> findList(@RequestBody(required = false) Para para){
        List<Para> paras = paraService.findList(para);
        return new Result<List<Para>>(true,StatusCode.OK,"条件查询成功！",paras);
    }
    /***
     * Para分页查询
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}")
    public  Result<PageInfo<Para>> findPage(@PathVariable(value = "page")Integer page,@PathVariable(value = "size")Integer size){
        PageInfo<Para> pagePageInfo = paraService.findPage(page, size);
        return new Result<PageInfo<Para>>(true,StatusCode.OK,"分页查询成功！",pagePageInfo);
    }
    @PostMapping(value = "/search/{page}/{size}")
    public   Result<PageInfo<Para>> findPage(@RequestBody(required = false) Para para,@PathVariable(value = "page")Integer page,@PathVariable(value = "size")Integer size){
        PageInfo<Para> paraPageInfos = paraService.findPage(para, page, size);
        return  new Result<PageInfo<Para>>(true,StatusCode.OK,"分页按条件查询成功！",paraPageInfos);
    }
}
