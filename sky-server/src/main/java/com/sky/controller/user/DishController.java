package com.sky.controller.user;


import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Api("C端-菜品浏览接口")
@Slf4j
public class DishController {


    @Autowired
    private DishService dishService;


//    @Autowired
//    private RedisTemplate redisTemplate;


    @GetMapping("/list")
    @ApiOperation("根据分类id查询分类列表")
    @Cacheable(cacheNames = "dishCache",key = "#categoryId")
    public Result<List<DishVO>> list(Long categoryId){

//        //构造redis中的key，规则，dish_分类Id
//        String key = "dish_" + categoryId;
//        //查询redis中是否存在菜品数据
//        List<DishVO> dishVOList = (List<DishVO>) redisTemplate.opsForValue().get(key);
//        //如果存在，直接返回，无须查询数据库
//        if(dishVOList!=null&&dishVOList.size()>0){
//            return Result.success(dishVOList);
//        }
        log.info("根据分类id查询分类列表：{}",categoryId);

        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);

        //如果不存在，查询数据库，将查询到的数据放入redis中
        List<DishVO> dishVOList = dishService.listWithFlavor(dish);
//        redisTemplate.opsForValue().set(key,dishVOList);
        return Result.success(dishVOList);
    }
}
