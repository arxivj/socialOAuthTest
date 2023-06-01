//package com.me.socialoauthtest.openbanking.controller;
//
//
//import com.me.socialoauthtest.openbanking.BankingFeign;
//import com.me.socialoauthtest.openbanking.dto.OpenRequest;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@CrossOrigin("*")
//public class OpenController {
//
//
//    // 생성자 주입
//    private final BankingFeign bankingFeign;
//
//    public OpenController(BankingFeign bankingFeign) {
//    this.bankingFeign = bankingFeign;
//    }
//
//
//
//
//    @Value("${openbanking.client.id}")
//    private String clientId;
//
//    @Value("${openbanking.client.secret}")
//    private String ClientSc;
//
//
//    @RequestMapping(value = "/openbanking", method = RequestMethod.POST)
//    public String loginUrlOpen() {
//        String reqUrl = "https://testapi.openbanking.or.kr/oauth/2.0/authorize?client_id=" + clientId
//                + "&response_type=code&redirect_uri=http://localhost/requesttoken&scope=login inquiry transfer&state=12345678123456781234567812345678&auth_type=0";
//        System.out.println("reqUrl : " + reqUrl);
//        return reqUrl;
//    }
//
//
//    @GetMapping("/requesttoken")
//    public String token(@RequestParam("code") String code,
//                        @RequestParam("scope") String scope,
//                        @RequestParam("state") String state) {
//
//
//        OpenRequest tokenReponse = bankingFeign.requestToken(code,
//                "86dd1ec4-2394-4815-963f-0e5d2c28428a",
//                "c3cb34d6-8b7d-4e3e-b2e7-aabf2f3d9f2d",
//                "http://localhost/requesttoken",
//                "authorization_code");
//        System.out.println(tokenReponse);
//        return null;
//    }
//
//}
