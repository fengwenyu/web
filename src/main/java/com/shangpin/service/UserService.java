package com.shangpin.service;

import com.shangpin.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by wenyu on 2016/9/22.
 */
@Service
public class UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> getList(){
        String sql = "SELECT id ,'name' ,age   FROM user";
        return (List<User>) jdbcTemplate.query(sql, new RowMapper<User>(){
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User stu = new User();
                stu.setId(rs.getString("id"));
                stu.setAge(rs.getString("age"));
                stu.setName(rs.getString("name"));
                return stu;
            }

        });
    }
}
