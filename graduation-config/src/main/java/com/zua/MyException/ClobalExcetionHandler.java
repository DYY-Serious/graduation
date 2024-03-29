package com.zua.MyException;

import com.zua.self.SelfException;
import com.zua.util.ExceptionEnum;
import com.zua.util.R;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ClobalExcetionHandler {
    /**
     * 自定义业务异常拦截
     * SelfException
     */
    @ExceptionHandler(SelfException.class)
    public R selfexception(SelfException e) {
        return R.ERROR(e.getMessage(),e.getCode());
    }


    /**
     * 未知的运行时异常拦截
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R notFount(RuntimeException e) {
        System.out.println(e);
        return R.ERROR(e.getMessage(),ExceptionEnum.SERVER_ERROR.getMessage());
    }
}
