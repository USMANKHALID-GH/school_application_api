package com.zalisoft.zalisoft.controller;


import com.zalisoft.zalisoft.dto.BaseResponseDto;
import com.zalisoft.zalisoft.dto.ParameterDto;
import com.zalisoft.zalisoft.enums.ResponseMessageEnum;
import com.zalisoft.zalisoft.mapper.ParameterMapper;
import com.zalisoft.zalisoft.model.Parameter;
import com.zalisoft.zalisoft.service.ParameterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/super")
public class ParameterController {

    @Autowired
    private ParameterService service;

    @Autowired
    private ParameterMapper mapper;

    @GetMapping("/parameter")
    public ResponseEntity<Page<ParameterDto>> search(@RequestParam(value = "searchKey", required = false) String searchKey, Pageable pageable) {
        Page<Parameter> searchResult = service.search(searchKey, pageable);
        return ResponseEntity.ok(new PageImpl<>(mapper.toDto(searchResult.getContent()), pageable, searchResult.getTotalElements()));
    }

    @PostMapping("/parameter")
    public ResponseEntity<ParameterDto> create(@RequestBody ParameterDto parameterDto) {
        log.info("create: {}", parameterDto.toString());
        return ResponseEntity.ok(mapper.toDto(service.create(parameterDto)));
    }

    @GetMapping("/parameter/{id}")
    public ResponseEntity<ParameterDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }

    @PutMapping("/parameter/{id}")
    public ResponseEntity<ParameterDto> update(@PathVariable Long id, @RequestBody ParameterDto parameterDto) {
        return ResponseEntity.ok(mapper.toDto(service.update(id, parameterDto)));
    }

    @DeleteMapping("/parameter/{id}")
    public ResponseEntity<BaseResponseDto> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message(ResponseMessageEnum.BACK_PARAMETER_MSG_004.messageDetail()).build());
    }
}