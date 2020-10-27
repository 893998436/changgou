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
@RequestMapping("/activity")
public interface ActivityFeign {

    /***
     * Activity分页条件搜索实现
     * @param activity
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@RequestBody(required = false) Activity activity, @PathVariable int page, @PathVariable int size);

    /***
     * Activity分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size);

    /***
     * 多条件搜索品牌数据
     * @param activity
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<Activity>> findList(@RequestBody(required = false) Activity activity);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable Integer id);

    /***
     * 修改Activity数据
     * @param activity
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody Activity activity, @PathVariable Integer id);

    /***
     * 新增Activity数据
     * @param activity
     * @return
     */
    @PostMapping
    Result add(@RequestBody Activity activity);

    /***
     * 根据ID查询Activity数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Activity> findById(@PathVariable Integer id);

    /***
     * 查询Activity全部数据
     * @return
     */
    @GetMapping
    Result<List<Activity>> findAll();
}