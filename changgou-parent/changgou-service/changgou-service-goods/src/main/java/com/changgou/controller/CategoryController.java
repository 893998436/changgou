package com.changgou.controller;

import com.changgou.goods.pojo.Category;
import com.changgou.service.CategoryService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    /***
     * 查询所有Category
     * @return
     */
    @GetMapping
    public Result<List<Category>> findAll(){
        List<Category> categorys = categoryService.findAll();
        return  new Result<List<Category>>(true, StatusCode.OK,"查询全部成功！",categorys);
    }
    /**
     * 根据ID查询Category
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public  Result<Category> findById(@PathVariable(value = "id")Integer id){
        Category category = categoryService.findById(id);
        return  new Result<Category>(true,StatusCode.OK,"根据Id查询成功！",category);
    }
    /***
     * 新增Category
     * @param category
     */
    @PostMapping
    public  Result add(@RequestBody Category category){
        categoryService.add(category);
        return  new Result(true,StatusCode.OK,"添加成功！");
    }

    /***
     * 修改Category数据
     * @param
     */
    @PutMapping(value = "/{id}")
    public  Result update(@PathVariable(value = "id")Integer id,@RequestBody Category category){
        category.setId(id);
        categoryService.update(category);
        return new Result(true,StatusCode.OK,"修改成功！");
    }
    /***
     * 删除Category
     * @param id
     */
    @DeleteMapping(value = "/{id}")
    public  Result delete(@PathVariable(value = "id")Integer id){
        categoryService.delete(id);
        return  new Result(true,StatusCode.OK,"删除成功！");
    }

    /***
     * Category多条件搜索方法
     * @param category
     * @return
     */
    @PostMapping(value = "/search")
    public  Result<List<Category>> findList(@RequestBody(required = false)Category category){
        List<Category> categories = categoryService.findList(category);
        return new  Result<List<Category>>(true,StatusCode.OK,"按条件查询成功！",categories);
    }
    /***
     * Category分页查询
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}")
    public  Result<PageInfo<Category>>  findPage(@PathVariable(value = "page")Integer page,@PathVariable(value = "size")Integer size){
        PageInfo<Category> pageInfo = categoryService.findPage(page, size);
        return new Result<PageInfo<Category>>(true,StatusCode.OK,"分页查询成功！",pageInfo);
    }

        /***
         * Category多条件分页查询
         * @param category
         * @param page
         * @param size
         * @return
         */
    @PostMapping(value = "/search/{page}/{size}")
    public  Result<PageInfo<Category>> findPage(@RequestBody(required = false)Category category,@PathVariable(value = "page")Integer page,@PathVariable(value = "size")Integer size){
        PageInfo<Category> pageInfo = categoryService.findPage(category, page, size);
        return  new Result<PageInfo<Category>>(true,StatusCode.OK,"按条件分页查询成功！",pageInfo);
    }

    /**
     * 根据父ID查询
     */
    @RequestMapping(value ="/list/{pid}")
    public Result<Category> findByPrantId(@PathVariable(value = "pid")Integer pid){
        //根据父节点ID查询
        List<Category> list = categoryService.findByParentId(pid);
        return new Result<Category>(true,StatusCode.OK,"查询成功",list);
    }
}
