package com.xyxy.mall.controller;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyxy.mall.common.lang.Result;
import com.xyxy.mall.pojo.Cart;
import com.xyxy.mall.pojo.Product;
import com.xyxy.mall.service.impl.CartServiceImpl;
import com.xyxy.mall.service.impl.ProductServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-09-06
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartServiceImpl cartService;

    @Autowired
    ProductServiceImpl productService;

    /*
    * 购物车及商品信息
    * 返回的商品List集合
    * */

    @GetMapping("/selectListPage")
    public Result selectListPage(@RequestBody @RequestParam("id") String id,
                                 @RequestParam(defaultValue = "1")int current,
                                 @RequestParam(defaultValue = "5")int number)
    {
        Page<Map<String, Object>> page = productService.selectListPage(current, number,id);
        return Result.success(page.getRecords());
    }

    /*查询用户购物车里的全部商品*/
    @GetMapping("/selCarProAll")
    public Result selCarProAll(String userid){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("userid",userid);
       List<Cart> list=cartService.list(queryWrapper);
       System.out.println(" 用户:"+list);
       List<String> proIdList=new ArrayList();
       for (int i=0;i<list.size();i++){
           proIdList.add(list.get(i).getProid());
       }
       Collection<Product> proList=productService.listByIds(proIdList);

       if (proList.size()!=0){
           return Result.success(proList);
       }else {
           return Result.fail("查询失败");
       }
    }

    /*添加商品到购物车
    * 参数为：
    *       proid:商品id
    *       userid:用户id
    *       quantity:商品数量
    * */
    @PostMapping("/insProToCart")
    public Result insProToCart(@RequestBody JSONObject jsonParam){
        System.out.println(jsonParam);
        String userid= jsonParam.getStr("userid");
        String proid= jsonParam.getStr("proid");
        int  quantity= Integer.parseInt(jsonParam.getStr("quantity"));
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("userid",userid);
        queryWrapper.eq("proid",proid);
        Cart cart=cartService.getOne(queryWrapper);
        if (cart==null){
            //如果该商品不存在则添加
            boolean reslut=cartService.save(new Cart().setProid(proid).setUserid(userid).setQuantity(quantity)
                    .setCreatetime(LocalDateTime.now()).setUpdatetime(LocalDateTime.now()));
            if (reslut==true){
                return Result.success("添加成功，在购物车等亲~");
            }else {
                return Result.fail("添加失败");
            }
        }else {
            //如果该商品存在则数量相加,并修改更新时间
            int proQuantity=cart.getQuantity()+quantity;
            UpdateWrapper updateWrapper=new UpdateWrapper();
            updateWrapper.eq("carid",cart.getCarid());
            boolean result=cartService.update(new Cart().setQuantity(proQuantity).setUpdatetime(LocalDateTime.now()),updateWrapper);
            if (result==true){
                return Result.success("添加成功，在购物车等亲~");
            }else {
                return Result.fail("添加失败");
            }
        }
    }

    /*删除已选中的购物车里的商品*/
    @DeleteMapping("/delOnCarProduct")
    public Result delOnCarProduct(String userid){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("userid",userid);
        queryWrapper.eq("checked",1);
        List<Cart> proList=cartService.list(queryWrapper);
        List<String> cartIdList=new ArrayList();
        for (int i=0;i<proList.size();i++){
            cartIdList.add(proList.get(i).getCarid());
        }
        boolean result=cartService.removeByIds(cartIdList);
        if (result=true){
            return Result.success(result);
        }else {
            return Result.fail("删除失败");
        }
    }


    /*点击选中商品
    * test
    * */
    @PostMapping("/onClickPro")
    public Result onClickPro(String proid,String userid){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("userid",userid);
        queryWrapper.eq("proid",proid);
       Cart cart=cartService.getOne(queryWrapper);
        try {
            boolean checkpoint=cart.getChecked()!=1;
        } catch (NullPointerException e) {//如果存在空指针则直接改为1（选中）
            //e.printStackTrace();
            boolean result=cartService.updateById(new Cart().setCarid(cart.getCarid()).setChecked(1));
            return Result.success(result);
        }

        if (cart.getChecked()!=1){
           //如果商品未勾选(状态为0或为null)则更改
           boolean result=cartService.updateById(new Cart().setCarid(cart.getCarid()).setChecked(1));
           if (result==true){
               return Result.success(result);
           } else {
               return Result.fail("更新失败");
           }
       }else {
           //已勾选则直接返回
           return Result.fail("商品已勾选");
       }
    }

    /*取消选中商品*/
    @PostMapping("/offClickPro")
    public Result offClickPro(String proid,String userid){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("userid",userid);
        queryWrapper.eq("proid",proid);
        Cart cart=cartService.getOne(queryWrapper);
        if(cart.getChecked()==1){
           // 如果商品为已勾选则更改
            boolean result=cartService.updateById(new Cart().setCarid(cart.getCarid()).setChecked(0));
            if (result==true){
                return Result.success(result);
            } else {
                return Result.fail("更新失败");
            }
        }else{
            //如果商品未勾选则直接返回
            return Result.fail("商品状态未勾选");
        }
    }

}
