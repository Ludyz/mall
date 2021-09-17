package com.xyxy.mall.controller;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyxy.mall.common.lang.Result;
import com.xyxy.mall.pojo.Cart;
import com.xyxy.mall.pojo.Product;
import com.xyxy.mall.service.ICartService;
import com.xyxy.mall.service.impl.CartServiceImpl;
import com.xyxy.mall.service.impl.ProductServiceImpl;
import io.swagger.annotations.Authorization;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-09-06
 */
@RequiresAuthentication//认证权限
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
    @PostMapping("/delOnCarProduct")
    public Result delOnCarProduct(@RequestBody String jsonObject){
        JSONArray jsonArray= JSONUtil.parseArray(jsonObject);
        List<String> list=new ArrayList<>();
        for (Object i:jsonArray) {
            JSONObject object=JSONUtil.parseObj(i);
            list.add(object.get("carid").toString());
        }
        boolean bo=cartService.removeByIds(list);
        if (bo==true){
            return Result.success("删除成功");
        }
        return Result.fail("删除失败") ;
    }

    /**
     * 改变购物车状态
     * @return
     */
    @PostMapping("/changeCarChecked")
    public Result changeChecked(@RequestBody String jsonParam){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");        JSONArray jsonArray= JSONUtil.parseArray(jsonParam);
        List<Cart> list=new ArrayList<>();
        for (Object i:jsonArray) {
            JSONObject object=JSONUtil.parseObj(i);
            Cart cart=new Cart();
            cart.setCarid(object.get("carid").toString());
            cart.setChecked((Integer)object.get("checked"));
            cart.setQuantity((Integer)object.get("quantity"));
            cart.setProid(object.get("proid").toString());
            cart.setUserid(object.get("userid").toString());
            cart.setCreatetime(LocalDateTime.parse(object.get("createtime").toString(),DateTimeFormatter.ISO_OFFSET_DATE_TIME));
            cart.setUpdatetime(LocalDateTime.now());
            list.add(cart);
        }

        boolean bo=cartService.updateBatchById(list,list.size());
        System.out.println("a");
        if(bo==true){
            return Result.success("成功");
        }
        return Result.fail("失败");
    }

}
