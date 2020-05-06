package edu.hhu.construction.dao;

import edu.hhu.construction.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserDao {

    /**
     * 根据id查询，返回User
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    public User getById(@Param("id") long id);

}
