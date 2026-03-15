package com.alvez.user_service.mapper;

import com.alvez.user_service.domain.User;
import com.fiap.user_service.users.dto.CreateUserRequestDTO;
import com.fiap.user_service.users.dto.UserDTO;


public class UserMapper {

  private UserMapper(){
    throw new IllegalStateException("Utility class");
  }


  public static User toEntity(final CreateUserRequestDTO createUserRequestDTO){
      return new User(createUserRequestDTO.getName(), createUserRequestDTO.getEmail());
  }

  public static UserDTO toDTO(final User created){
    return new UserDTO()
              .id(created.getId())
              .name(created.getName())
              .email(created.getEmail());
  }

}