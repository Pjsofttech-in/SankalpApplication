package com.sankalpapp.dynamicProfile.controller;

import com.sankalpapp.dto.Request.LoginRequest;
import com.sankalpapp.dto.Response.LoginResponse;
import com.sankalpapp.dynamicProfile.serviceImpl.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
public class StaffController
{
    @Autowired
    private StaffService staffLoginService;

    @PostMapping("/stafflogin")
    public Mono<ResponseEntity<LoginResponse>> loginStaff(@RequestBody LoginRequest request) {
        return staffLoginService.loginStaff(request)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> {
                    LoginResponse errorResponse = new LoginResponse();
                    errorResponse.setToken(null);
                    errorResponse.setData(Map.of("error", "Login failed: " + e.getMessage()));
                    return Mono.just(ResponseEntity.status(500).body(errorResponse));
                });
    }

    @GetMapping("/permissionForStaff")
    public Map<String, Boolean> getPermission(@RequestParam String staffEmail) {
        return staffLoginService.getPermissionsByEmail(staffEmail);
    }


    @GetMapping("/permissionForDepartment")
    public ResponseEntity<Map<String, Object>> getDepartmentPermissions(@RequestParam String departmentEmail) {

        Map<String, Object> permissions = staffLoginService.getCrudPermissionForDepartmentByEmail(departmentEmail);
        return ResponseEntity.ok(permissions);
    }

}
