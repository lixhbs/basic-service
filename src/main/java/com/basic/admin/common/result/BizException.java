package com.basic.admin.common.result;

import com.basic.admin.common.enums.BizCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lix.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BizException extends RuntimeException
{
    private Integer code;
    private String msg;

    public BizException(int code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BizException(BizCodeEnum bizCodeEnum){
        super(bizCodeEnum.getMessage());
        this.code = bizCodeEnum.getCode();
        this.msg = bizCodeEnum.getMessage();
    }
}
