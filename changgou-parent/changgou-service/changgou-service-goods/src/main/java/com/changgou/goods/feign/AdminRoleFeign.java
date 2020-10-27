package com.changgou.goods.feign;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/****
 * @Author:shenkunlin
 * @Description:
 * @Date 2019/6/18 13:58
 *****/
@FeignClient(name="goods")
@RequestMapping("/adminRole")
public interface AdminRoleFeign {

    /***
     * AdminRole分页条件搜索实现
     * @param adminRole
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@RequestBody(required = false) AdminRole adminRole, @PathVariable int page, @PathVariable int size);

    /***
     * AdminRole分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size);

    /***
     * 多条件搜索品牌数据
     * @param adminRole
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<AdminRole>> findList(@RequestBody(required = false) AdminRole adminRole);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable Integer id);

    /***
     * 修改AdminRole数据
     * @param adminRole
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody AdminRole adminRole, @PathVariable Integer id);

    /***
     * 新增AdminRole数据
     * @param adminRole
     * @return
     */
    @PostMapping
    Result add(@RequestBody AdminRole adminRole);

    /***
     * 根据ID查询AdminRole数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<AdminRole> findById(@PathVariable Integer id);

    /***
     * 查询AdminRole全部数据
     * @return
     */
    @GetMapping
    Result<List<AdminRole>> findAll();
}