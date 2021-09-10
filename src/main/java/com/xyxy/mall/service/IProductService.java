package com.xyxy.mall.service;

import com.xyxy.mall.pojo.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-09-06
 */
public interface IProductService extends IService<Product> {
    public String SavePicture(MultipartFile file);

}
