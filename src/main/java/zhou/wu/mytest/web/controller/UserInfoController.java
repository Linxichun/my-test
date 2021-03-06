package zhou.wu.mytest.web.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zhou.wu.mytest.web.annotation.DemoAnnotation;
import zhou.wu.mytest.web.domain.AutoUserInfo;
import zhou.wu.mytest.web.service.UserInfoService;

/**
 * @author Lin.xc
 * @date 2019/10/12
 */
@Slf4j
@RestController
@RequestMapping("/user-info")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping
    @DemoAnnotation(value = "insertUserInfo", primaryId = "#userInfo.userage")
    public AutoUserInfo insertUserInfo(@RequestBody AutoUserInfo userInfo){
        AutoUserInfo autoUserInfo = userInfoService.selectByUserNo(userInfo.getUserno());
//        if(autoUserInfo==null){
//            userInfoService.insertAutoUserInfo(userInfo);
//        }else{
//            log.info("无需插入！[{}]", JSON.toJSONString(userInfo));
//        }
        System.out.println("虚的插入！");
//        return Boolean.TRUE;
        return autoUserInfo;
    }

    @PutMapping
    public Boolean updateUserInfo(@RequestBody AutoUserInfo userInfo){
        userInfoService.updateAutoUserInfo(userInfo);
        return Boolean.TRUE;
    }

    /**
     * 事务测试-疑似幻读
     * */
    @GetMapping("/tx-test1/{userNo}")
    public Boolean txTest1(@PathVariable String userNo){
        return userInfoService.txTest1(userNo);
    }

    /**
     * 事务测试-脏读
     * */
    @GetMapping("/tx-test2/{userNo}")
    public Boolean txTest2(@PathVariable String userNo){
       return userInfoService.txTest2(userNo);
    }

}
