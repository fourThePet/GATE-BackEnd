package com.ureca.gate;

import com.ureca.gate.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/")
    public SuccessResponse<String> test(){
        System.out.println("test1");
        return SuccessResponse.success("ㅎ");
    }
    @GetMapping("/health")
    public SuccessResponse<String> test1(){
        System.out.println("test2");
        return SuccessResponse.successWithoutResult("ㅎ");
    }
}
