package com.snatch.dao;

import com.snatch.domain.Goods;
import com.snatch.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: UserDao
 * @description:
 * @author: nping
 * @create: 2020-03-01 15:07
 **/
@Repository
@Mapper
public interface GoodsTestDao {
    /**
     * 根据用户id查询用户的信息。
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    public User getUserById(@Param("id") Long id);

    @Select("select * from goods")
    public List<Goods> getUserList();
}
