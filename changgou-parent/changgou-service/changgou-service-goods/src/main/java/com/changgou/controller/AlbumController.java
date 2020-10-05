package com.changgou.controller;

import com.changgou.goods.pojo.Album;
import com.changgou.service.AlbumService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/album")
@CrossOrigin
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    /*
     * 查找Album所有数据
     * */
    @GetMapping
    public Result<Album> selectALL() {
        List<Album> list = albumService.findAll();
        return new Result<Album>(true, StatusCode.OK, "查询成功!", list);
    }

    /**
     * 根据ID查询Album
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Album> selectById(@PathVariable Long id) {
        Album album = albumService.findById(id);
        return new Result<Album>(true, StatusCode.OK, "查询成功!", album);
    }

    /***
     * 新增Album
     * @param album
     * */

    @PostMapping
    public Result add(@RequestBody Album album) {
        albumService.add(album);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /***
     * 修改Album数据
     * @param album
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Album album, @PathVariable Long id) {
        //设置主键
        album.setId(id);
        //修改数据
        albumService.update(album);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /***根据ID删除品牌数据
     * 删除Album
     * @param id
     */
    @DeleteMapping(value = "/{id}")
    public Result delect(@PathVariable Long id) {
        albumService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * Album条件查询
     *
     * @param album
     * @return
     */
    @PostMapping(value = "/search")
    public Result<List<Album>> findList(@RequestBody(required = false) Album album) {
        List<Album> albums = albumService.findList(album);
        return new Result(true, StatusCode.OK, "查询成功!", albums);
    }

    /***
     * Album分页查询
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        PageInfo<Album> pageInfo = albumService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "条件查询成功！", pageInfo);
    }

    /***
     * Album多条件分页查询
     * @param album
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Album album, @PathVariable int page, @PathVariable int size) {
        //执行搜索
        PageInfo<Album> pageInfo = albumService.findPage(album, page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功！", pageInfo);
    }
}
