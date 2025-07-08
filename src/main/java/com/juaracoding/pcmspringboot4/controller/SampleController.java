package com.juaracoding.pcmspringboot4.controller;

import com.juaracoding.pcmspringboot4.core.SMTPCore;
import com.juaracoding.pcmspringboot4.util.ReadTextFileSB;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class SampleController {

    @GetMapping("/email")
    public Object sendMail(){
        SMTPCore sc = new SMTPCore();
        String [] strEmail = {"satria.pandega@gmail.com","poll.chihuy@gmail.com"};
        sc.sendMailWithAttachment(strEmail,
                "TESTING SMTP",
                "INI TESTING DOANK JGN DI REPLAY",
                "TLS",null);
        return "OK";
    }

    @GetMapping("/email-content")
    public Object sendMailContent() throws Exception {
        String[] strVerify = new String[3];
        strVerify[0] = "KIRIM EMAIL";
        strVerify[1] = "PAUL C";
        strVerify[2] = "242388";
        String  strContent = new ReadTextFileSB("ver_regis.html").getContentFile();
        strContent = strContent.replace("#JKVM3NH",strVerify[0]);//Kepentingan
        strContent = strContent.replace("XF#31NN",strVerify[1]);//Nama Lengkap
        strContent = strContent.replace("8U0_1GH$",strVerify[2]);//TOKEN
        final String content = strContent;
        SMTPCore sc = new SMTPCore();
        String [] strEmail = {"satria.pandega@gmail.com","poll.chihuy@gmail.com"};
        sc.sendMailWithAttachment(strEmail,
                "TESTING SMTP",
                content,
                "TLS",null);
        return "OK";
    }
}