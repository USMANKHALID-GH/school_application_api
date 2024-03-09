package com.usman.mapper;

import com.usman.dto.BaseDto;

import java.util.List;

public interface BaseMapper<D extends BaseDto, E> {
    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    List<D> toDto(List<E> entityList);
}
