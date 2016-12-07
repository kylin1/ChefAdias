package web.biz;

import org.springframework.web.multipart.MultipartFile;
import web.tools.MyMessage;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Alan on 2016/12/6.
 */
public interface ShopFoodService {
    /**
     * 商家上传食物情况
     *
     * @param name        食物名
     * @param pic         食物图片
     * @param typeID      食物类别ID
     * @param price       食物单价
     * @param foodIDList  额外食物ID列表
     * @param description 简介
     * @return MyMessage
     */
    MyMessage addFood(String name, MultipartFile pic, int typeID, BigDecimal price, List<String> foodIDList, String description);

    /**
     * 商家删除食物
     *
     * @param foodID 食物ID
     * @return MyMessage
     */
    MyMessage deleteFood(int foodID);

    /**
     * 修改食物信息
     *
     * @param foodID      食物ID
     * @param name        食物名
     * @param pic         食物图片
     * @param price       食物单价
     * @param foodIDList  额外食物ID列表
     * @param description 介绍
     * @return MyMessage
     */
    MyMessage modFood(int foodID, String name, MultipartFile pic, BigDecimal price, List<String> foodIDList, String description);

    /**
     * 创建分类
     *
     * @param name 食物名称
     * @param pic  食物图片
     * @return MyMessage
     */
    MyMessage addType(String name, MultipartFile pic);

    /**
     * 删除分类
     *
     * @param typeID 分类ID
     * @return MyMessage
     */
    MyMessage deleteType(int typeID);

    /**
     * 修改分类
     *
     * @param typeID 分类ID
     * @param name   分类名称
     * @param pic    分类图片
     * @return MyMessage
     */
    MyMessage modType(int typeID, String name, MultipartFile pic);

}
