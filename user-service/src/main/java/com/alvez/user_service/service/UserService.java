package com.alvez.user_service.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.alvez.user_service.domain.UserSpecification;
import com.alvez.user_service.exception.UserAlreadyExistsException;
import com.alvez.user_service.exception.UserNotFoundException;
import com.alvez.user_service.mapper.UserMapper;
import com.alvez.user_service.repository.UserRepository;
import com.fiap.user_service.users.dto.CreateUserRequestDTO;
import com.fiap.user_service.users.dto.PaginatedResultDTO;
import com.fiap.user_service.users.dto.UpdateUserRequestDTO;
import com.fiap.user_service.users.dto.UserDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService{
  
  
  private final UserRepository userRepository;


  public UserDTO creatUser(final CreateUserRequestDTO createUserRequestDTO){
    if( userRepository.existsByEmail(createUserRequestDTO.getEmail())){
    log.warn("User with email {} already exists", createUserRequestDTO.getEmail());
    throw new UserAlreadyExistsException(createUserRequestDTO.getEmail());
    }

    final var toCreate = UserMapper.toEntity(createUserRequestDTO);
    final var created =  userRepository.save(toCreate);
    return UserMapper.toDTO(created);
  }

  public void deleteUserById(Long id){
    userRepository.deleteById(id);
  }

  @Transactional
  public PaginatedResultDTO getAllUsers(final Integer page, final Integer size, final String sort, final String name, final String email){

    final var sortParams = sort.split(",");
    final var sortField = sortParams[0];
    final var sortDirection = sortParams[1];
    final var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
    final var specification = UserSpecification.create(name, email);
    final var users = userRepository.findAll(specification, pageable);
    final var totalElements = users.getTotalElements();
    final var totalPages = users.getTotalPages();
    final var userDtos = users.getContent().stream().map(UserMapper::toDTO).toList();

    return new PaginatedResultDTO().page(page).size(size).totalElements(totalElements).totalPages(totalPages).content(userDtos);
  }

  @Transactional
  public UserDTO getUserById(final Long id){
    return userRepository.findById(id).map(UserMapper::toDTO).orElseThrow(() -> new UserNotFoundException(id));
  }

  public UserDTO updateUser(final Long id, final UpdateUserRequestDTO updateUserRequestDTO){
    final var userToUpdate = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
    userToUpdate.setName(updateUserRequestDTO.getName());
    userToUpdate.setEmail(updateUserRequestDTO.getEmail());

    final var updated = userRepository.save(userToUpdate);
    return UserMapper.toDTO(updated); 
  }

}
