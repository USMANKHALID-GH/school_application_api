package com.zalisoft.zalisoft.controller;

import com.zalisoft.zalisoft.dto.BaseResponseDto;
import com.zalisoft.zalisoft.dto.PersonalDto;
import com.zalisoft.zalisoft.dto.PersonalShortDto;
import com.zalisoft.zalisoft.dto.UserInformationDto;
import com.zalisoft.zalisoft.mapper.PersonalMapper;
import com.zalisoft.zalisoft.mapper.PersonalShortMapper;
import com.zalisoft.zalisoft.model.Personal;
import com.zalisoft.zalisoft.repository.FacultyRepository;
import com.zalisoft.zalisoft.service.PersonalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api")
public class PersonalController {


    @Autowired
    private PersonalService service;



    @Autowired
    private PersonalShortMapper mapper;

    @PutMapping("/admin/personal")
    public ResponseEntity<BaseResponseDto>  updateByUser(@RequestBody UserInformationDto userInformationDto) {
        service.updatepersonal(userInformationDto);
        return  ResponseEntity.ok(BaseResponseDto.builder().message("Personal saved successfully").build());
    }

    @GetMapping("/super/personal/faculty/{facultyId}")
    public ResponseEntity<Page<PersonalShortDto>>  getPersonalsByFaculty(@PathVariable Long facultyId, Pageable pageable) {
        Page<Personal> searchResult = service. getPersonalsByFaculty(pageable,facultyId);
        return ResponseEntity.ok(new PageImpl<>(mapper.toDto(searchResult.getContent()), pageable, searchResult.getTotalElements()));
    }

    @GetMapping("/admin/personal/department")
    public ResponseEntity<Page<PersonalShortDto>> findAllDepartmentPersonal(Pageable pageable) {
        log.info("findAllDepartmentPersonal: ");
        Page<Personal> searchResult = service.findAllDepartmentPersonal(pageable);
        return ResponseEntity.ok(new PageImpl<>(mapper.toDto(searchResult.getContent()), pageable, searchResult.getTotalElements()));
    }

    @GetMapping("/super/personal")
    public ResponseEntity<Page<PersonalShortDto>> search(@RequestParam(value = "searchKey", required = false) String searchKey, Pageable pageable) {
        Page<Personal> searchResult = service.search(searchKey, pageable);
        return ResponseEntity.ok(new PageImpl<>(mapper.toDto(searchResult.getContent()), pageable, searchResult.getTotalElements()));
    }

    @DeleteMapping("/admin/personal/{id}")
    public ResponseEntity<BaseResponseDto> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Personal deleted successfully").build());
    }

    @GetMapping("/all/personal/{id}")
    public ResponseEntity<PersonalShortDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }

    @GetMapping("/super/personal/phone/{phone}")
    public ResponseEntity<PersonalShortDto> findByEmailOrPhoneOrTc(@PathVariable String phone) {
        return ResponseEntity.ok(mapper.toDto(service.findByEmailOrPhoneOrTc(phone)));
    }


    @PutMapping("/super/personal/{id}")
    public ResponseEntity<BaseResponseDto> updateByAdmin(@PathVariable Long id, @RequestBody UserInformationDto userInformationDto) {
        service.updateByAdmin(id, userInformationDto);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Personal updated successfully").build());
    }

}
