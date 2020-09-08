package com.changgou.controller;


import com.changgou.goods.pojo.Brand;
import com.changgou.service.BrandService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/brand")
@CrossOrigin  //跨域：A域名访问B域名的数据

public class BrandController {
    @Autowired
    private BrandService brandService;

    /*
     *查询所有品牌
     * */
    @GetMapping
    public Result<List<Brand>> findAll() {
        List<Brand> brands = brandService.findAll();
        //响应结果封装
        return new Result<List<Brand>>(true, StatusCode.OK, "查询品牌集合成功!", brands);
    }

    /*
     * 根据id查询
     * */
    @GetMapping(value = "/{id}")
    public Result<Brand> findById(@PathVariable(value = "id") Integer id) {
        //调用Service实现查询
        Brand brand = brandService.findById(id);
        //响应数据封装
        return new Result<Brand>(true, StatusCode.OK, "根据品牌ID查询成功!", brand);
    }

    /*
     * 增加品牌
     *
     * */
    @PostMapping
    public Result add(@RequestBody Brand brand) {
// 调用service实现添加
        brandService.add(brand);

        return new Result(true, StatusCode.OK, "增加品牌成功!");
    }

    /*
     * 品牌修改实现
     * */
    @PutMapping(value = "/{id}")
    public Result update(@PathVariable(value = "id") Integer id, @RequestBody Brand brand) {
        //将ID给brand
        brand.setId(id);
        brandService.update(brand);
        return new Result(true, StatusCode.OK, "品牌修改成功!");
    }

    /*
     *根据ID删除品牌
     *
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable(value = "id") Integer id) {
        brandService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除品牌成功!");
    }

    //条件查询
    @PostMapping(value = "/search")
    public Result<List<Brand>> findList(@RequestBody Brand brand) {
        List<Brand> brands = brandService.findList(brand);
        return new Result<List<Brand>>(true, StatusCode.OK, "条件查询成功!", brands);
    }

    //分页查询
    @GetMapping(value = "/search/{page}/{size}")
    public Result<PageInfo<Brand>> findPage(@PathVariable(value = "page") Integer page, @PathVariable(value = "size") Integer size) {
        PageInfo<Brand> pageInfo=brandService.findPage(page, size);
        return new Result<PageInfo<Brand>>(true, StatusCode.OK, "分页查询成功!",pageInfo);
    }

    //分页+条件查询
    @PostMapping (value = "/search/{page}/{size}")
    public Result<PageInfo<Brand>> findPage(@RequestBody Brand brand, @PathVariable(value = "page") Integer page, @PathVariable(value = "size") Integer size) {
        PageInfo<Brand> pageInfo=brandService.findPage(page, size);
        return new Result<PageInfo<Brand>>(true, StatusCode.OK, "分页查询成功!",pageInfo);
    }
}
