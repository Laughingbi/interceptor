package com.handle.globalhandle.starter.exception;

import com.handle.globalhandle.starter.utils.APIResponse;
import com.handle.globalhandle.starter.utils.ResponeCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Error")
public class ErrorController {


    @GetMapping("/401")
    public APIResponse Error401()  {
        return new APIResponse(ResponeCode.UNAUTHORIZED,"401","权限不足",null);
    }

    @GetMapping("/403")
    public APIResponse Error403()  {
        return new APIResponse(ResponeCode.NO_PERMISSION,"403","操作过于频繁",null);
    }
}

