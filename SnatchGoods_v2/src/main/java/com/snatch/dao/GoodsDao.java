package com.snatch.dao;

import com.snatch.domain.Goods;
import com.snatch.domain.SnatchGoods;
import com.snatch.vo.GoodsDetailVo;
import com.snatch.vo.GoodsListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: GoodsDao
 * @description:
 * @author: nping
 * @create: 2020-03-01 16:37
 **/
@Repository
@Mapper
public interface GoodsDao {
    /**
     * 获取商品列表
     * @return
     */
    @Select("select * from goods;")
    public List<Goods> getGoodsList();

    /**
     * 返回商品的列表信息，包括：
     *  商品的名称
     *  秒杀价格
     *  开始时间
     *  结束时间
     *  秒杀库存数量等
     * @return
     */
    @Select("select g.id,g.goods_name,mg.snatch_stock,mg.start_date,mg.end_date,mg.snatch_price from snatch_goods mg left join goods g on mg.goods_id=g.id")
    public List<GoodsListVo> getGoodsListVos();

    /**
     * 根据id返回商品的详细信息
     * @param id
     * @return
     */
    @Select("select g.*,mg.snatch_stock,mg.start_date,mg.snatch_price from snatch_goods mg left join goods g on mg.goods_id = g.id where g.id = #{id}")
    GoodsDetailVo getGoodsDetailById(@Param("id") Long id);

    /**
     * 根据商品信息返回当前秒杀商品的库存。
     * @param goodsId
     * @return
     */
    @Select("select snatch_stock from snatch_goods where goods_id = #{goodsId}")
    int countOfSnatchStock(@Param("goodsId") Long goodsId);

    /**
     * 修改秒杀商品的库存
     * @param goodsId
     * @return
     */
    @Update("update snatch_goods set snatch_stock = snatch_stock -1 where goods_id = #{goodsId} and snatch_stock > 0")
    int reduceStock(@Param("goodsId") Long goodsId);

    /**
     *根据商品id返回商品的对象
     * @param goodsId
     * @return
     */
    @Select("select * from goods where id = #{goodsId}")
    Goods getGoodsById(@Param("goodsId") Long goodsId);

    @Select("select g.*,mg.snatch_stock,mg.start_date,mg.snatch_price from snatch_goods mg left join goods g on mg.goods_id = g.id")
    List<GoodsDetailVo> getSnatchGoodsDetailList();
}
