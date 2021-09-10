package com.xyxy.mall.pojo;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2021-09-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Cart对象", description="")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "carid",type = IdType.UUID)
    private String carid;

    @ApiModelProperty(value = "用户表id")
    private String userid;

    @ApiModelProperty(value = "商品id")
    private String proid;

    @ApiModelProperty(value = "数量")
    private Integer quantity;

    @ApiModelProperty(value = "是否选择,1=已勾选,0=未勾选")
    private Integer checked;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createtime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatetime;


}
