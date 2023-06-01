package com.me.socialoauthtest.openbanking;
import com.me.socialoauthtest.openbanking.dto.OpenRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name="feign", url="https://testapi.openbanking.or.kr")
public interface BankingFeign {

    // 등록된 URL에 접속해서 값을 받아와서 VO에 넣어준다..

    @PostMapping(path = "/oauth/2.0/token", consumes = "application/x-www-form-urlencoded", produces = "application/json")
    public OpenRequest requestToken(@RequestParam("code") String code ,
                                    @RequestParam("client_id") String client_id,
                                    @RequestParam("client_secret") String client_secret,
                                    @RequestParam("redirect_uri") String redirect_uri,
                                    @RequestParam("grant_type") String grant_type);
}