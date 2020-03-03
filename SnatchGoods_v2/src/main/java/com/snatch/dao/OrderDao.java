package com.snatch.dao;

import com.snatch.domain.Order;
import com.snatch.domain.OrderInfo;
import com.snatch.domain.SnatchGoods;
import com.snatch.domain.SnatchOrder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: OrderDao
 * @description:
 * @author: nping
 * @create: 2020-03-01 20:11
 **/
@Mapper
@Repository
public interface OrderDao {

    @Select("select * from snatch_order where user_id = #{userId} and goods_id = #{goodsId}")
    SnatchOrder getSnatchOrderByUserIdAndGoodsId(@Param("userId") Long userId,
                                                 @Param("goodsId") Long goodsId);

    //TODO:此处返回的订单编号有问题！还需要改进
    /**
     * 根据订单信息插入到订单中，并且返回插入订单的编号。
     * @param order 商品信息
     * @return 创建的订单的编号
     */
    @Insert("insert into `order` (user_id, goods_id, goods_name, goods_count, goods_price, status, create_date)" +
            "values("
            + "#{order.userId}, #{order.goodsId}, #{order.goodsName}, #{order.goodsCount}, " +
            "#{order.goodsPrice}, #{order.status},#{order.createDate} )")
    @SelectKey(keyColumn = "id",resultType = long.class,before = false,statement = "select last_insert_id()", keyProperty = "order.id")
    Long createOrder(@Param("order") Order order);

    //selectkey是用来返回id的。

    @Insert("insert into `order` (user_id, goods_id, goods_name, goods_count, goods_price, status, create_date)" +
            "values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{status},#{createDate} )")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = long.class,before = false,statement = "select last_insert_id()")
    long insert(Order orderInfo);

    @Insert("insert into `snatch_order` (user_id,goods_id,order_id) values(" +
            "#{userId},#{goodsId},#{orderId})")
    int insertSnatchOrder(SnatchOrder snatchOrder);

    @Delete("delete from snatch_order")
    int deleteAllSnatchOrder();
}
