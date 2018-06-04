package wyc.block.exception;


import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import wyc.block.util.CommonUtility;

import java.util.HashMap;


/**
 * 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = Logger.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handle(Exception e) {
        log.error(e.getClass().toString(),e);

        String result=null;
        try {
            String message= "服务器内部异常";
            result= CommonUtility.constructResultJson("-1", message,new HashMap());
        } catch (BlockException e1) {
            e1.printStackTrace();
        }
        return result;
    }



}
