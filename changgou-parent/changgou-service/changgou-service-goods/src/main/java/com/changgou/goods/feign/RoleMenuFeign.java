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
@RequestMapping("/roleMenu")
public interface RoleMenuFeign {

    /***
     * RoleMenu分页条件搜索实现
     * @param roleMenu
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@RequestBody(required = false) RoleMenu roleMenu, @PathVariable int page, @PathVariable int size);

    /***
     * RoleMenu分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size);

    /***
     * 多条件搜索品牌数据
     * @param roleMenu
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<RoleMenu>> findList(@RequestBody(required = false) RoleMenu roleMenu);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable Integer id);

    /***
     * 修改RoleMenu数据
     * @param roleMenu
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody RoleMenu roleMenu, @PathVariable Integer id);

    /***
     * 新增RoleMenu数据
     * @param roleMenu
     * @return
     */
    @PostMapping
    Result add(@RequestBody RoleMenu roleMenu);

    /***
     * 根据ID查询RoleMenu数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<RoleMenu> findById(@PathVariable Integer id);

    /***
     * 查询RoleMenu全部数据
     * @return
     */
    @GetMapping
    Result<List<RoleMenu>> findAll();
}