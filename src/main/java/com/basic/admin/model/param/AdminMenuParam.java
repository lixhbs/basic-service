package com.basic.admin.model.param;

import com.basic.admin.common.result.PageableBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class AdminMenuParam extends PageableBO implements Serializable
{
}
