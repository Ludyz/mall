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
@ApiModel(value="Orders对象", description="")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单id")
    private String orderid;

    @ApiModelProperty(value = "用户id")
    private String userid;

    private String shoppingid;

    @ApiModelProperty(value = "实际付款金额,单位是元,保留两位小数")
    private BigDecimal payment;

    @ApiModelProperty(value = "支付类型,1-在线支付")
    private Integer paymenttype;

    @ApiModelProperty(value = "订单状态:0-已取消-10-未付款，20-已付款，40-已发货，50-交易成功，60-交易关闭")
    private Integer status;

    @ApiModelProperty(value = "支付时间")
    private LocalDateTime paymenttime;

    @ApiModelProperty(value = "交易完成时间")
    private LocalDateTime endtime;

    @ApiModelProperty(value = "交易关闭时间")
    private LocalDateTime closetime;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createtime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatetime;


}
