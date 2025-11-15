package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.security.Authority;
import com.datajoy.admin_builder.security.AuthorityRepository;
import com.datajoy.admin_builder.security.UserGroupAuthority;
import com.datajoy.admin_builder.security.UserGroupAuthorityRepository;
import com.datajoy.admin_builder.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController("console.UserGroupAuthorityRestController")
@RequestMapping("/console/api/user-group/authority")
public class UserGroupAuthorityRestController {
    @Autowired
    private UserGroupAuthorityRepository repository;
    @Autowired
    private UserGroupRepository userGroupRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    @GetMapping("/{userGroupId}")
    public ResponseEntity<?> get(@PathVariable("userGroupId") Long userGroupId) {
        List<UserGroupAuthority> results = repository.findByUserGroupId(userGroupId);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Map<String,Object> params) {
        Long userGroupId = Long.valueOf((String) params.get("userGroupId"));
        Long authorityId = Long.valueOf((String) params.get("authorityId"));

        UserGroup userGroup = userGroupRepository.findById(userGroupId)
                .orElseThrow(RuntimeException::new);

        Authority authority = authorityRepository.findById(authorityId)
                .orElseThrow(RuntimeException::new);

        UserGroupAuthority createdData = UserGroupAuthority.builder()
                .authority(authority)
                .userGroup(userGroup)
                .build();

        return new ResponseEntity<>(repository.save(createdData), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        UserGroupAuthority savedData = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        Long userGroupId = Long.valueOf((String) params.get("userGroupId"));
        Long authorityId = Long.valueOf((String) params.get("authorityId"));

        UserGroup userGroup = userGroupRepository.findById(userGroupId)
                .orElseThrow(RuntimeException::new);

        Authority authority = authorityRepository.findById(authorityId)
                .orElseThrow(RuntimeException::new);

        savedData.update(userGroup, authority);

        repository.save(savedData);

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        UserGroupAuthority savedData = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        repository.deleteById(savedData.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
