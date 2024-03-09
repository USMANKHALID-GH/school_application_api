package com.usman.controller;

import com.usman.dto.BaseResponseDto;
import com.usman.dto.RoleDto;
import com.usman.mapper.RoleMapper;
import com.usman.model.Role;
import com.usman.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/super/role")
public class RoleController {

    @Autowired
    private RoleService service;

    @Autowired
    private RoleMapper mapper;

    @GetMapping
    public ResponseEntity<List<RoleDto>> findAll() {
        List<Role> searchResult = service.findAll();
        return ResponseEntity.ok(mapper.toDto(searchResult));
    }

    @PostMapping
    public ResponseEntity<RoleDto> create(@RequestBody RoleDto roleDto) {
        return ResponseEntity.ok(mapper.toDto(service.create(mapper.toEntity(roleDto))));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<RoleDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> update(@PathVariable Long id, @RequestBody RoleDto roleDto) {
        return ResponseEntity.ok(mapper.toDto(service.update(id, roleDto)));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BaseResponseDto> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Rol silme işlemi başarılı olarak tamamlanmıştır.").build());
    }

}
