package com.software.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Wang Hao
 * @date 2022/10/2 23:33
 */
@Data
@TableName("user")
public class User implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private Integer id;

    @TableField(value = "name")
    @ApiModelProperty(value = "姓名")
    private String name;

    @TableField(value = "password")
    @ApiModelProperty(value = "密码")
    private String password;
}
