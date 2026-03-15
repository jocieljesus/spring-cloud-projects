package com.alvez.user_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.alvez.user_service.service.UserService;
import com.fiap.user_service.users.api.UsersApi;
import com.fiap.user_service.users.dto.CreateUserRequestDTO;
import com.fiap.user_service.users.dto.PaginatedResultDTO;
import com.fiap.user_service.users.dto.UpdateUserRequestDTO;
import com.fiap.user_service.users.dto.UserDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi{

   @NotNull
   private final UserService userService;


   @Override
   public ResponseEntity<UserDTO> createUser(@Valid final CreateUserRequestDTO body){
      return ResponseEntity.ok(userService.creatUser(body));
   }

   @Override
   public ResponseEntity<Void> deleteUserById(final Long userId){
      userService.deleteUserById(userId);
      return ResponseEntity.noContent().build();
   }

   @Override
   public ResponseEntity<PaginatedResultDTO> getAllUsers( @Valid final Integer page, @Valid final Integer size,  @Valid final String sort, @Valid final String name, @Valid final String email ){
      return ResponseEntity.ok(userService.getAllUsers(page, size, sort, name, email));
   }

   @Override
   public ResponseEntity<UserDTO> getUserById(final Long userId){
      return ResponseEntity.ok(userService.getUserById(userId));
   }

   @Override
   public ResponseEntity<UserDTO> updateUserById( final Long userId, @Valid final UpdateUserRequestDTO body){
      return ResponseEntity.ok(userService.updateUser(userId, body));
   }
}
