package com.xyxy.mall.pojo;

import java.math.BigDecimal;
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
@ApiModel(value="Orderitem对象", description="")
public class Orderitem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单子表id")
    private String id;

    @ApiModelProperty(value = "订单id")
    private String orderid;

    @ApiModelProperty(value = "用户表id")
    private String userid;

    @ApiModelProperty(value = "商品id")
    private String proid;

    @ApiModelProperty(value = "商品名称")
    private String proname;

    @ApiModelProperty(value = "商品图片地址")
    private String proimage;

    @ApiModelProperty(value = "生成订单时的商品单价，单位是元,保留两位小数")
    private BigDecimal currentunitprice;

    @ApiModelProperty(value = "商品数量")
    private Integer quantity;

    @ApiModelProperty(value = "商品总价,单位是元,保留两位小数")
    private BigDecimal totalprice;

    private LocalDateTime createtime;

    private LocalDateTime updatetime;


}
