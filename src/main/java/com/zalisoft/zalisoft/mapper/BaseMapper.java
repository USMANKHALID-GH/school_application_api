package com.zalisoft.zalisoft.mapper;

import com.zalisoft.zalisoft.dto.BaseDto;
import com.zalisoft.zalisoft.model.AbstractModel;

import java.util.List;

public interface BaseMapper<D extends BaseDto, E> {
    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    List<D> toDto(List<E> entityList);
}
