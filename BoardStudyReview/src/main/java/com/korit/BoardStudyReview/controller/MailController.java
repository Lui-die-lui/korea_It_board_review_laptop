package com.korit.BoardStudyReview.controller;

import com.korit.BoardStudyReview.dto.mail.SendMailReqDto;
import com.korit.BoardStudyReview.sequrity.model.PrincipalUser;
import com.korit.BoardStudyReview.service.MailService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMail(@RequestBody SendMailReqDto sendMailReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(mailService.sendMail(sendMailReqDto,principalUser));
    }

    @GetMapping("verify")
    public String verify(Model model, @RequestParam String verifyToken) {
        Map<String, Object> reusltMap = mailService.verify(verifyToken);
        model.addAllAttributes(reusltMap);
        return "result_page";
    }

}



