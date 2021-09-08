package com.xyxy.mall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyxy.mall.common.lang.Result;
import com.xyxy.mall.pojo.Product;
import com.xyxy.mall.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

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
    @PostMapping("/insProduct")
    public Result insProduct(@RequestBody Product product){
        boolean result= iProductService.save(product);
        if (result==true){
            return Result.success(result);
        }else {
            return Result.fail("插入失败");
        }
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
    @GetMapping("/selProductById")
    public Result selProductById(String proid){
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

    /*  分页查询产品信息*/
    @GetMapping("/selProductPage")
    public Result selProductPage(int pageNo,int pageSize,String proid){
        IPage<Product> page=new Page(pageNo,pageSize);
        QueryWrapper<Product> wrapper=new QueryWrapper<>();
        Product product=new Product();
        product.setProid(proid);
        wrapper.setEntity(product);
        IPage iPage=iProductService.page(page,wrapper);
        return Result.success(iPage);
    }

    /*根据ID更新单个商品*/
    @PutMapping("/updProductById")
    public Result updProductById(Product product){
        boolean result=iProductService.updateById(product);
        if (result==true){
            return Result.success(result);
        }else {
            return Result.fail("更新失败");
        }
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

    /*根据商品名称更新商品信息*/
    @PutMapping("/updProductByName")
    public Result updProductByName(Product product,String name){
        UpdateWrapper<Product> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("name",name);
        boolean result=iProductService.update(product,updateWrapper);
        if (result==true){
            return Result.success(result);
        }else {
            return Result.fail("更新失败");
        }
    }

    public void test(){}
}
