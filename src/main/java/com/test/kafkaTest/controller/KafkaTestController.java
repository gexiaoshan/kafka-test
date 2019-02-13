package com.test.kafkaTest.controller;

import com.test.kafkaTest.kafkaConf.KafkaResetOffset;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * Created by gexiaoshan on 2019/2/13.
 * spring与kafka结合 生产消息 重置消息
 */
@Api(description = "验证码接口")
@Slf4j
@RestController
@RequestMapping("/kafka")
public class KafkaTestController {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private KafkaResetOffset kafkaResetOffset;


    @ApiOperation("生产消息")
    @GetMapping("/producer")
    public String producer() {
        for (int i = 0; i < 10; i++) {
            kafkaTemplate.send("test1", "消息" + i);
        }
        return "success";
    }

    @ApiOperation("重置消息")
    @PutMapping("/reset/offset")
    public String reset(@RequestParam("topic") String topic,
                                @RequestParam("groupName") String groupName,
                                @RequestParam("time") String time) {
        kafkaResetOffset.resetOffset(topic, groupName, time);
        return "success";
    }

}
