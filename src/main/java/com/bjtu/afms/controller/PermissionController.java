package com.bjtu.afms.controller;

import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.config.annotation.AuthCheck;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Result;
import com.bjtu.afms.model.Permission;
import com.bjtu.afms.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @Resource
    private PermissionBiz permissionBiz;

    @GetMapping("/permission/all")
    public Result getAllPermission() {
        return Result.ok(AuthType.getAllPermissionType());
    }

    @GetMapping({"/resource/owner/list", "/resource/owner/list/{page}"})
    public Result getResourceOwner(@RequestParam("type") String type, @RequestParam("relateId") int relateId,
                                   @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(permissionBiz.getResourceOwnerList(type, relateId, page));
    }


    @PostMapping("/resource/owner/add")
    public Result addResourceOwner(@RequestParam("type") int type, @RequestParam("relateId") int relateId,
                                   @RequestParam("userId") int userId) {
        if (permissionBiz.addResourceOwner(type, relateId, userId)) {
            return Result.ok();
        } else {
            return Result.error(APIError.INSERT_ERROR);
        }
    }

    @AuthCheck(auth = AuthType.ADMIN)
    @PostMapping("/admin/permission/insert")
    public Result addPermissionByAdmin(@RequestBody Permission permission) {
        if (permissionBiz.addPermission(permission)) {
            return Result.ok();
        } else {
            return Result.error(APIError.INSERT_ERROR);
        }
    }

    @AuthCheck(auth = AuthType.ADMIN)
    @PostMapping("/admin/permission/delete/{permissionId}")
    public Result deletePermissionByAdmin(@PathVariable("permissionId") int permissionId) {
        if (permissionService.deletePermission(permissionId) == 1) {
            return Result.ok();
        } else {
            return Result.error(APIError.DELETE_ERROR);
        }
    }

    @AuthCheck(auth = AuthType.ADMIN)
    @PostMapping("/admin/permission/delete/user/{userId}")
    public Result deleteUserPermissionByAdmin(@PathVariable("userId") int userId) {
        permissionService.deleteUserPermission(userId);
        return Result.ok();
    }

    @AuthCheck(auth = AuthType.ADMIN)
    @GetMapping({"/admin/permission/list/user/{userId}", "/admin/permission/list/user/{userId}/{page}"})
    public Result getUserPermissionListByAdmin(@PathVariable("userId") int userId,
                                               @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(permissionBiz.getUserPermissionList(userId, page));
    }

    @AuthCheck(auth = AuthType.ADMIN)
    @GetMapping({"/admin/permission/user/list", "/admin/permission/user/list/{page}"})
    public Result getPermissionUserList(@RequestParam("auths") List<Integer> auths,
                                        @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(permissionBiz.getPermissionUserList(auths, page));
    }
}
