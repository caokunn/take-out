package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐相关接口")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;


    /**
     * 新增套餐
     * @param setmealDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增套餐")
    @CacheEvict(cacheNames = "setmealCache",key = "#setmealDTO.categoryId") //key: setmealCache::100
    public Result save(@RequestBody SetmealDTO setmealDTO){
        log.info("新增套餐：{}",setmealDTO);
        setmealService.save(setmealDTO);
        return Result.success();
    }//在管理端增、删、改方法返回结果是不带有数据的，用@CachePut注解无法将数据存入Redis

    /**
     * 分页查询套餐
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询套餐")
    public Result<PageResult> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("分页查询套餐，{}",setmealPageQueryDTO);
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("批量删除套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result delete(@RequestParam List<Long> ids){
        log.info("批量删除套餐，{}",ids);
        setmealService.deleteBatch(ids);
        return Result.success();
    }


    /**
     * 根据id查询套餐及其菜品
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐及其菜品")
    public Result<SetmealVO> getById(@PathVariable Long id){
        log.info("根据id查询套餐及其菜品，{}",id);
        SetmealVO setmealVO = setmealService.getByIdWithDish(id);
        return Result.success(setmealVO);
    }


    /**
     * 修改套餐
     * @param setmealDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result update(@RequestBody SetmealDTO setmealDTO){
        log.info("修改套餐，{}",setmealDTO);
        setmealService.update(setmealDTO);
        return Result.success();
    }


    @PostMapping("/status/{status}")
    @ApiOperation("起售停售套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result startOrStopSell(@PathVariable Integer status,
                                  Long id){
        log.info("起售停售套餐，{}，{}",status,id);
        setmealService.startOrStopSell(status,id);
        return Result.success();
    }
}
