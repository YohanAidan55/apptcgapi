package com.aidan.apptcg.user.repository.mapper;

import com.aidan.apptcg.user.domain.dto.UserDTO;
import com.aidan.apptcg.user.repository.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract UserEntity toEntity(UserDTO userDTO);

    public abstract UserDTO toDto(UserEntity userEntity);

    public abstract List<UserDTO> toDto(List<UserEntity> userEntities);
}

