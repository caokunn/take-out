package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DishService {

    /**
     * 新增菜品和对应的口味数据
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 菜品的批量删除功能
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据id查询菜品和口味数据
     * @param id
     * @return
     */
    DishVO getByIdWithFlavor(Long id);

    /**
     * 根据id修改菜品基本信息和对应的口味信息
     * @param dishDTO
     */
    void updateWithFlavor(DishDTO dishDTO);

    /**
     * 菜品停售起售
     * @param id
     * @param status
     */
    void startOrStopSell(Long id, Integer status);

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    List<Dish> getByCategoryId(Long categoryId);


    /**
     * 根据分类id和起售状态条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);




}
