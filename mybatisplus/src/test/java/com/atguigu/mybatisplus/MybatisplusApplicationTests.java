package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.entity.User;
import com.atguigu.mybatisplus.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisplusApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println("测试");
    }
    @Autowired
    private UserMapper userMapper;
    @Test
    public void testSelect() {
        System.out.println(userMapper);
        List<User> userList = userMapper.selectList(null);
        for (User user : userList) {
            System.out.println(user);
        }
    }
    @Test
    public void testInsert(){

        User user = new User();
        user.setName("Tom");
        user.setAge(22);
        user.setEmail("55317332@qq.com");

        int result = userMapper.insert(user);
        System.out.println(result); //影响的行数
        System.out.println(user); //id自动回填
    }
    @Test
    public void testUpdateById(){

        User user = new User();
        user.setId(1L);
        user.setAge(28);
        user.setName("Tom");

        int result = userMapper.updateById(user);
        System.out.println(result);
    }
    @Test
    public void testOptimisticLocker() {//乐观锁
        //查询
        User user = userMapper.selectById(1L);
        //修改数据
        user.setName("Helen Yao");
        user.setEmail("helen@qq.com");
        //执行更新
        userMapper.updateById(user);
    }
    @Test
    public void testOptimisticLockerFail() {// 乐观锁失败
        //查询
        User user = userMapper.selectById(1L);
        //修改数据
        user.setName("Helen Yao1");
        user.setEmail("helen1@qq.com");
        //模拟另一个线程中间更新了数据
        //查询
        User user2 = userMapper.selectById(1L);
        //修改数据
        user2.setName("Helen Yao2");
        user2.setEmail("helen2@qq.com");
        userMapper.updateById(user2);
        //执行更新
        userMapper.updateById(user);
    }
    @Test
    public void testSelectById(){// 根据id查询记录
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }
    @Test
    public void testSelectBatchIds(){// 通过多个id批量查询
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2));
        users.forEach(System.out::println);
    }
    @Test
    public void testSelectByMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Helen");
        map.put("age", 18);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }
    @Test
    public void testSelectPage() {
        Page<User> page = new Page<>(1,3);//当前页码，每页记录数
        userMapper.selectPage(page, null);
        page.getRecords().forEach(System.out::println);// 获取当前页的集合数据
        System.out.println(page.getCurrent());
        System.out.println(page.getPages());
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
    }
    @Test
    public void testDeleteById(){
        int result = userMapper.deleteById(2L);
        System.out.println(result);
    }
    @Test
    public void testDeleteBatchIds() {
        int result = userMapper.deleteBatchIds(Arrays.asList(3,4));
        System.out.println(result);
    }
    @Test
    public void testDeleteByMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Billie");
        map.put("age", 24);
        int result = userMapper.deleteByMap(map);
        System.out.println(result);
    }
    /**
     * 测试 逻辑删除
     */
    @Test
    public void testLogicDelete() {
        int result = userMapper.deleteById(6L);
        System.out.println(result);
    }
    /**
     * 测试 性能分析插件
     */
    @Test
    public void testPerformance() {
        User user = new User();
        user.setName("我是Helen");
        user.setEmail("helen@sina.com");
        user.setAge(18);
        userMapper.insert(user);
    }
    /**
     * 测试 逻辑删除后的查询：
     * 不包括被逻辑删除的记录
     */
    @Test
    public void testLogicDeleteSelect() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

}
