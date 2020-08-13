package com.maoyang.transaction.service;

import com.maoyang.transaction.annnoction.ATTransaction;
import com.maoyang.transaction.mapper.UserMapper;
import com.maoyang.transaction.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * 服务层
 *
 * @author maoyang
 */
@Service
public class UserService {
    private UserMapper userDao;

    private RestTemplate restTemplate;

    @Autowired
    public UserService(UserMapper userDao, RestTemplate restTemplate) {
        this.userDao = userDao;
        this.restTemplate = restTemplate;
    }

    @Transactional(rollbackFor = {Exception.class})
    @ATTransaction
    public void save() {
        User user = new User();
        user.setUserName("李三");
        user.setAge(30);
        userDao.insert(user);

        User user2 = new User();
        user2.setUserName("远程调用");
        user2.setAge(88);
        restTemplate.postForEntity("http://127.0.0.1:8086/saveUserInfo", user2, String.class);
    }

   @Transactional(rollbackFor = {Exception.class})
  //  @ATTransaction
    public void save2() {
        User user = new User();
        user.setUserName("王五");
        user.setAge(21);
        try {
            userDao.insert(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
