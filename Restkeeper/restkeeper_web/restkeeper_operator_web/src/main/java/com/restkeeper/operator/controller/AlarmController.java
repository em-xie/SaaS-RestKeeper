package com.restkeeper.operator.controller;

import com.restkeeper.operator.dto.AlarmDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @作者：xie
 * @时间：2023/5/1 16:08
 */
@Slf4j
@RestController
@RequestMapping("/alarm")
public class AlarmController {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String from;

    /**
     * 接收skywalking发送的告警通知并发送至邮箱
     */
    @PostMapping("/receive")
    public void receive(@RequestBody List<AlarmDTO> alarmList) {
        SimpleMailMessage message = new SimpleMailMessage();
        // 发送者邮箱
        message.setFrom(from);
        // 接收者邮箱
        message.setTo(from);
        // 主题
        message.setSubject("告警邮件");
        String content = getContent(alarmList);
        // 邮件内容
        message.setText(content);
        mailSender.send(message);
        log.info("告警邮件已发送...");
    }

    private String getContent(List<AlarmDTO> alarmList) {
        StringBuilder sb = new StringBuilder();
        for (AlarmDTO dto : alarmList) {
            sb.append("scopeId: ").append(dto.getScopeId())
                    .append("\nscope: ").append(dto.getScope())
                    .append("\n目标 Scope 的实体名称: ").append(dto.getName())
                    .append("\nScope 实体的 ID: ").append(dto.getId0())
                    .append("\nid1: ").append(dto.getId1())
                    .append("\n告警规则名称: ").append(dto.getRuleName())
                    .append("\n告警消息内容: ").append(dto.getAlarmMessage())
                    .append("\n告警时间: ").append(dto.getStartTime())
                    .append("\n\n---------------\n\n");
        }
        return sb.toString();
    }
}