package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.entity.User;
import com.atguigu.mybatisplus.mapper.UserMapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisplusApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void contextLoads() {
        System.out.println("测试");
    }
    @Test
    public void testSelectList() {
        System.out.println(("----- selectAll method test ------"));
        //UserMapper 中的 selectList() 方法的参数为 MP 内置的条件封装器 Wrapper
        //所以不填写就是无任何条件
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }
    //添加数据
    @Test
    public void testInsert(){

        User user = new User();

        user.setAge(24);
        user.setEmail("55317337@qq.com");

        int result = userMapper.insert(user);
        System.out.println(result); //影响的行数
        System.out.println(user); //id自动回填
    }
    @Test
    public void  testUpdateById(){
        User user = new User();
        user.setId(7L);
        user.setName("jerry");
        user.setAge(28);
        int result = userMapper.updateById(user);
        System.out.println(result);
    }
    @Test
    public void testOptimisticLocker(){
        //查询
        User user = userMapper.selectById(7);
        //修改数据
        user.setName("Hello Yao");
        user.setEmail("hello@qq.com");
        //执行更新
        userMapper.updateById(user);
    }
    /**
     * 测试乐观锁插件失败
     */
    @Test
    public void testOptimisticLockerFail(){
        //查询
        User user = userMapper.selectById(7);
        //修改数据
        user.setName("Hello Yao1");
        user.setEmail("hello1@qq.com");
        //模拟另一个线程中间更新了数据
        //查询
        User user2 = userMapper.selectById(7);
        //修改数据
        user2.setName("Hele0 Yao2");
        user2.setEmail("hele02@qq.com");
        //执行更新
        userMapper.updateById(user2);
        userMapper.updateById(user);

    }
    @Test
    public void testSelectById(){
        User user = userMapper.selectById(9L);
        System.out.println(user);
    }
    /**
     * 通过多个id批量查询
     */
    @Test
    public void testSelectBatchIds(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(9L,10L,12L));
        users.forEach(System.out::println);
    }
    /**
     * 通过map封装查询条件
     */
    @Test
    public void testSelectByMap(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("name","Jack");
        map.put("age",18);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }
    @Test
    public void testSelectPage(){
        Page<User> page = new Page<>(1,2);
        userMapper.selectPage(page,null);
        page.getRecords().forEach(System.out::println);
        System.out.println(page.getCurrent());
        System.out.println(page.getPages());
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
    }
    @Test
    public void testSelectMapsPage(){
        Page<User> page = new Page<>(1,2);
        IPage<Map<String, Object>> mapIPage = userMapper.selectMapsPage(page, null);

        //注意：此行必须使用 mapIPage 获取记录列表，否则会有数据类型转换错误
        mapIPage.getRecords().forEach(System.out::println);
        System.out.println(page.getCurrent());
        System.out.println(page.getPages());
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
    }
    /**
     * 根据id删除记录
     *
     */
    @Test
    public void testDeleteById(){
        int result = userMapper.deleteById(9L);
        System.out.println(result);
    }
    /**
     * 批量删除
     */
    @Test
    public void testDeleteBatchIds(){
        int result = userMapper.deleteBatchIds(Arrays.asList(7,8,11));
        System.out.println(result);
    }
    /**
     * 简单的条件查询删除
     */
    @Test
    public void testDeleteByMap(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("age",29);
        int result = userMapper.deleteByMap(map);
        System.out.println(result);
    }
    /**
     * 逻辑删除
     */
    @Test
    public void testLogicDelete(){
        int result = userMapper.deleteById(10L);
        System.out.println(result);
    }
    /**
     * 测试 逻辑删除后的查询：
     * 不包括被逻辑删除的记录
     */
    @Test
    public void testLogicDeleteSelect(){
        User user = new User();
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    /**
     * 测试 性能分析插件
     */
    @Test
    public void testPerformance(){
        User user = new User();
        user.setName("我是hello");
        user.setEmail("hello@gmail.com");
        user.setAge(22);
        userMapper.insert(user);
    }
    @Test
    public void testDelete(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .isNull("name")
                .ge("age",12)
                .isNotNull("email");
        int result = userMapper.delete(queryWrapper);
        System.out.println("delete return count =" + result);

    }
    @Test
    public void testSelectOne(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name","zhangsan");
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }
    @Test
    public void testSelectCount(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("age",20,25);
        Integer count = userMapper.selectCount(queryWrapper);
        System.out.println(count);
    }
    @Test
    public void testSelectList1(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String,Object> map = new HashMap<>();
        map.put("id",14);
        map.put("name","zhangsan");
        map.put("age",24);
        queryWrapper.allEq(map);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);

    }
    @Test
    public void testSelectMaps(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .notLike("name","w")
                .likeRight("email","5");
        List<Map<String,Object>> maps = userMapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);
    }
    @Test
    public void testSelectObjs(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //queryWrapper.in("id",13,14,15);
        queryWrapper.inSql("id","select id from user where id < 16");
        List<Object> objects = userMapper.selectObjs(queryWrapper);
        objects.forEach(System.out::println);
    }
    @Test
    public void testUpdate1(){
        //修改值
        User user = new User();
        user.setAge(36);
        user.setName("naruto");
        //修改条件
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper
                .like("name","z")
                .or()
                .between("age",20,30);
        int result = userMapper.update(user,userUpdateWrapper);
        System.out.println(result);
    }
    @Test
    public void testUpdate2(){
        //修改值
        User user = new User();
        user.setAge(22);
        user.setName("konan");
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper
                .like("name","n")
                .or(i -> i.eq("name","naruto").ne("age",20));
        int result = userMapper.update(user,userUpdateWrapper);
        System.out.println(result);

    }
    @Test
    public void testSelectisOrderBy(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
    @Test
    public void testSelectListLast(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("limit 2");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
    @Test
    public  void  testSelectListColumn(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name","age");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
    @Test
    public void testUpdateSet(){
        //修改值
        User user = new User();
        user.setAge(18);
        //修改条件
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper
                .like("name","k")
                .set("name","张三")
                .setSql("email = 'hutf@qq.com'");
        int result = userMapper.update(user,userUpdateWrapper);
    }


}
