package com.xyxy.mall.controller;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyxy.mall.common.lang.Result;
import com.xyxy.mall.pojo.Product;
import com.xyxy.mall.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ClassUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-09-06
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    IProductService iProductService;

    /*  插入单个商品*/
    @PostMapping(value = "insProduct",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result insProduct(HttpServletRequest request,@RequestParam(value = "product",required = true)String productJson,
                             @RequestParam(value = "mainimage",required = false) MultipartFile mainimage,
                             @RequestParam(value = "subimage",required = false) MultipartFile subimage){
        Product product= JSONUtil.toBean(productJson,Product.class);
        System.out.println(product);
        String mainFileName="";
        String subFileName="";
        if (mainimage!=null){
            mainFileName=iProductService.SavePicture(mainimage);
        }
        if (subimage!=null){
            subFileName=iProductService.SavePicture(subimage);
        }
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        Product productDb=product;
        productDb.setProid(uuid);
        productDb.setMainimage(mainFileName);
        productDb.setSubimages(subFileName);
        boolean bo=iProductService.save(product);
        if (!bo){
            return Result.fail("添加失败");
        }
        return  Result.success("添加成功");
    }

    /*  批量插入商品*/
    @PostMapping("/insProducts")
    public Result insProducts(List<Product> productList){
        boolean result= iProductService.saveBatch(productList);
        if (result==true){
            return Result.success(result);
        }else {
            return Result.fail("批量插入失败");
        }
    }

    /*  删除单个商品*/
    @DeleteMapping("/delProduct")
    public Result delProduct(String proid){
        boolean result=iProductService.removeById(proid);
        if (result==true){
            return Result.success(result);
        }else {
            return Result.fail("删除失败");
        }
    }

    /*  删除多个商品*/
    @DeleteMapping("delProducts")
    public Result delProducts(List proidList){
        boolean result=iProductService.removeByIds(proidList);
        if (result==true){
            return Result.success(result);
        }else {
            return Result.fail("删除失败");
        }
    }

    /*根据ID查询单个商品*/
    @GetMapping("/selProductById/{proId}")
    public Result selProductById(@PathVariable("proId") String proid){
        Product product=iProductService.getById(proid);
        if (product!=null){
            return Result.success(product);
        }else {
            return Result.fail("查询失败");
        }
    }

    /*根据ID查询多个商品*/
    @GetMapping("/selProductByIds")
    public Result selProductByIds(List<String> proidList){
        Collection collection=iProductService.listByIds(proidList);
        if (collection.size()==proidList.size()){
            return Result.success(collection);
        }else {
            return Result.fail("查询失败");
        }
    }

    /*  分页查询产品信息
    * 参数：
    *       pageNo:
    *       pageSize:
    *       proid:
    * */
    @GetMapping("/selProductPage")
    public Result selProductPage(@RequestParam(defaultValue = "1") int pageNo,
                                 @RequestParam(defaultValue = "5") int pageSize){
        Page page=new Page(pageNo,pageSize);
        QueryWrapper<Product> queryWrapper=new QueryWrapper<Product>();
        queryWrapper.orderByDesc("updatetime");
        IPage<Product> iPage=iProductService.page(page,queryWrapper);
        return Result.success(iPage);
    }

    /*根据ID更新单个商品*/
    @PostMapping("/updProductById/{proId}")
    public Result updProductById(@PathVariable("proId") String proId,
                                 @RequestParam(value = "product",required = true)String productJson,
                                 @RequestParam(value = "mainimage",required = false) MultipartFile mainimage,
                                 @RequestParam(value = "subimage",required = false) MultipartFile subimage){
        Product product= JSONUtil.toBean(productJson,Product.class);
        System.out.println(product);
        String mainFileName="";
        String subFileName="";
        if (mainimage!=null){
            mainFileName=iProductService.SavePicture(mainimage);
        }
        if (subimage!=null){
            subFileName=iProductService.SavePicture(subimage);
        }
        Product productDb=product;
        product.setMainimage(mainFileName);
        product.setSubimages(subFileName);
        boolean bo=iProductService.updateById(productDb);
        if (!bo){
            return Result.fail("更新失败");
        }
        return  Result.success("更新成功");

    }

    /*根据ID批量更新商品*/
    @PutMapping("/updProductByIds")
    public Result updProductByIds(List<Product> productList){
        boolean result=iProductService.updateBatchById(productList);
        if (result==true){
            return Result.success(result);
        }else {
            return Result.fail("更新失败");
        }
    }

    /*根据商品名称更新商品信息
    * test
    * */
    @PutMapping("/updProductByName")
    public Result updProductByName(Product product){
        UpdateWrapper<Product> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("name",product.getName());
        boolean result=iProductService.update(product,updateWrapper);
        if (result==true){
            return Result.success(result);
        }else {
            return Result.fail("更新失败");
        }
    }

    public void test(){}
}
