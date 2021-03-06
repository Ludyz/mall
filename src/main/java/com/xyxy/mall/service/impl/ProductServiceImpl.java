package com.xyxy.mall.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyxy.mall.pojo.Product;
import com.xyxy.mall.mapper.ProductMapper;
import com.xyxy.mall.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-09-06
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    public String SavePicture(MultipartFile file) {

        String path = "F:\\productPicture\\";
        String fileName = file.getOriginalFilename();
        String key = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String fullFileName = key + "-" + fileName;
        String fullPath = path + fullFileName;

        File dest = new File(fullPath);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fullFileName;
    }

    @Override
    public Page<Map<String, Object>> selectListPage ( int current, int number, String id){
        Page<Map<String, Object>> page = new Page<Map<String, Object>>(current, number);
        return page.setRecords(this.baseMapper.getAllCartproduct(page, id));
    }
}
