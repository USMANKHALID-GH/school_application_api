package com.zalisoft.zalisoft.controller;
import com.zalisoft.zalisoft.dto.BaseResponseDto;
import com.zalisoft.zalisoft.dto.FacultyDto;
import com.zalisoft.zalisoft.mapper.FacultyMapper;
import com.zalisoft.zalisoft.model.Faculty;
import com.zalisoft.zalisoft.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FacultyController {

    @Autowired
    private FacultyService service;

    @Autowired
    private FacultyMapper mapper;



    @GetMapping("/public/faculty")
    public ResponseEntity<Page<FacultyDto>> search(@RequestParam(value = "searchKey", required = false) String searchKey, Pageable pageable) {
        Page<Faculty> searchResult = service.search(searchKey, pageable);
        return ResponseEntity.ok(new PageImpl<>(mapper.toDto(searchResult.getContent()), pageable, searchResult.getTotalElements()));
    }

    @PostMapping("/super/faculty")
    public ResponseEntity<FacultyDto> create(@RequestBody FacultyDto facultyDto) {
        return ResponseEntity.ok(mapper.toDto(service.save(facultyDto)));
    }

    @GetMapping("/all/faculty/{id}")
    public ResponseEntity<FacultyDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }

    @PutMapping("/super/faculty/{id}")
    public ResponseEntity<BaseResponseDto> update(@PathVariable Long id, @RequestBody FacultyDto facultyDto) {
        service.update(id, facultyDto);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Faculty updated successfully").build());
    }

    @DeleteMapping("/super/faculty/{id}")
    public ResponseEntity<BaseResponseDto> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(BaseResponseDto.builder().message("Faculty deleted successfully").build());
    }
}
