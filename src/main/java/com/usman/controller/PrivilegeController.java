package com.usman.controller;

import com.usman.dto.BaseResponseDto;
import com.usman.dto.PrivilegeDto;
import com.usman.mapper.PrivilegeMapper;
import com.usman.service.PrivilegeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/super/privilege")
public class PrivilegeController {

    @Autowired
    private PrivilegeService service;

    @Autowired
    private PrivilegeMapper mapper;

    @GetMapping
    public ResponseEntity<List<PrivilegeDto>> findAll() {
        return ResponseEntity.ok(mapper.toDto(service.findAll()));
    }

    @PostMapping
    public ResponseEntity<PrivilegeDto> create(@RequestBody PrivilegeDto privilegeDto) {
        return ResponseEntity.ok(mapper.toDto(service.create(mapper.toEntity(privilegeDto))));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PrivilegeDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrivilegeDto> update(@PathVariable Long id, @RequestBody PrivilegeDto privilegeDto) {
        return ResponseEntity.ok(mapper.toDto(service.update(id, privilegeDto)));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BaseResponseDto> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Yetki silme işlemi başarılı olarak tamamlanmıştır.").build());
    }
}