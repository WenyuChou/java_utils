package com.zhou.utils.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/7/18
 * description : 悲观锁的实现
 */
@Mapper
public interface TestDao {

    @Select("select * from ble_device where id = #{id} for update")
    Map<String,Object> lockTest(@Param("id")Integer id);
}
