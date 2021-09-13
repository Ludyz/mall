package com.xyxy.mall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xyxy.mall.common.lang.Result;
import com.xyxy.mall.pojo.Category;
import com.xyxy.mall.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryServiceImpl categoryService;

    /*增加分类
    * 先根据名称查询是否存在，存在则插入失败，不存在则赋入时间插入
    * 参数：category中不需传入类别id及创建时间及更新时间
    * */
    public Result insCategory(Category category){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("name",category.getName());
        Category selCategory=categoryService.getOne(queryWrapper);
        if (selCategory==null){
            category.setCreatetime(LocalDateTime.now());
            category.setUpdatetime(LocalDateTime.now());
            boolean result=categoryService.save(category);
            if (result==true){
                return Result.success(result);
            }else {
                return Result.fail("插入失败");
            }
        }else {
            return Result.fail("分类已存在");
        }
    }

    /*删除分类
    * 删除前判断其是否有人以他为父分类，有也不删除，无则删除
    * */
    @DeleteMapping("/delCategory")
    public Result delCategory(String cateid){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("parentid",cateid);
        List cateList=categoryService.list(queryWrapper);
        if (cateList.isEmpty()){
            //没人以该节点为父节点，可删除
            boolean result=categoryService.removeById(cateid);
            if (result==true){
                return Result.success(result);
            }else {
                return Result.fail("删除失败");
            }
        }else {
            //有人以其为父节点，不可删除，提醒用户
            return Result.fail("删除失败，先删除子分类");
        }
    }

    /*根据ID修改分类
    * 参数：传入分类id(必须) 修改分类名称或分类状态或分类排序
    * */
    @PutMapping("/updCategory")
    public Result updCategory(Category category){
        category.setUpdatetime(LocalDateTime.now());
        boolean result= categoryService.updateById(category);
        if (result==true){
            return Result.success(result);
        }else {
            return Result.fail("更改失败");
        }
    }

    //查询出该父节点的子节点
    public Category selSonCate(String cateId){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("parentid",cateId);
        Category category=categoryService.getOne(queryWrapper);
        return category;
    }

    //查询出以该节点为父节点的子节点
    public List selCateByFu(String cateId){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("parentid",cateId);
        List<Category> cateList=categoryService.list(queryWrapper);//查询出所有根节点
        return cateList;
    }

    /*查询全部分类
    @GetMapping("/selAllCate")
    public Result selAllCate(){
        List<Category> cateList=selCateByFu("0");//查询出所有根节点
        List allList=new ArrayList();
        for (int i=0;i<cateList.size();i++){
            Category sonCategory=selSonCate(cateList.get(i).getCateid());//查出其子节点
            //用子节点的id查看是否有人以其为父节点，二级节点
            List sonCateList=selCateByFu(sonCategory.getCateid());
            if (sonCateList.size()==0){//没子节点以其为父节点（叶子节点）
                cateMap.put(cateList.get(i),sonCategory);
            }else {//有节点以其为父节点
                cateMap.put(cateList.get(i),new HashMap<Category,Object>(sonCategory))
                for ()
            }
        }
    }*/

    /**/
}
