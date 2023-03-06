package com.atguigu.mybatisplus.auth.service;


import com.atguigu.mybatisplus.pojo.model.system.SysMenu;
import com.atguigu.mybatisplus.pojo.vo.system.AssginMenuVo;
import com.atguigu.mybatisplus.pojo.vo.system.RouterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface SysMenuService extends IService<SysMenu> {

    //菜单列表接口
    List<SysMenu> findNodes();

    //删除菜单
    void removeMenuById(Long id);

    //查询所有菜单和角色分配的菜单
    List<SysMenu> findMenuByRoleId(Long roleId);

    //角色分配菜单
    void doAssign(AssginMenuVo assginMenuVo);

    //4 根据用户id获取用户可以操作菜单列表
    List<RouterVo> findUserMenuListByUserId(Long userId);

    //5 根据用户id获取用户可以操作按钮列表
    List<String> findUserPermsByUserId(Long userId);
}
