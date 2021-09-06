package com.xyxy.mall.pojo;

import java.time.LocalDateTime;
import java.io.Serializable;
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
@ApiModel(value="Category对象", description="")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类别Id")
    private String cateid;

    @ApiModelProperty(value = "父类别id当id=0时说明是根节点,一级类别")
    private String parentid;

    @ApiModelProperty(value = "类别名称")
    private String name;

    @ApiModelProperty(value = "类别状态1-正常,2-已废弃")
    private Integer status;

    @ApiModelProperty(value = "排序编号,同类展示顺序,数值相等则自然排序")
    private Integer sortorder;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createtime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatetime;


}
