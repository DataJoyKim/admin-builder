package com.datajoy.admin_builder.superadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/super-admin")
public class SuperAdminController {
    @Autowired
    SuperAdminService superAdminService;

    @GetMapping("/sysadmin/create")
    public ResponseEntity<?> createSysAdmin(@RequestParam("loginId") String loginId) {
        superAdminService.createSysAdmin(loginId);

        return new ResponseEntity<>("create complete", HttpStatus.OK);
    }
}
