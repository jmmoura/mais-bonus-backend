package com.josiel.maisbonus.service;

import com.josiel.maisbonus.dto.UserDTO;
import com.josiel.maisbonus.dto.mapper.UserMapper;
import com.josiel.maisbonus.enums.UserType;
import com.josiel.maisbonus.model.User;
import com.josiel.maisbonus.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    private CompanyService companyService;

    private UserMapper userMapper;

    public List<UserDTO> list() {
        return userMapper.toDTOList(userRepository.findAll());
    }

    public UserDTO findById(Long id) {
        return userRepository.findById(id).map(userMapper::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("User ID " + id + " not found"));
    }

    public UserDTO create(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);

        if (userDTO.getType().equals(UserType.CUSTOMER)) {
            user.setCompany(companyService.findByCnpj(userDTO.getCnpj()));
        }

        return userMapper.toDTO(userRepository.save(user));
    }

    public UserDTO update(Long id, UserDTO userDTO) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(userDTO.getName());
                    user.setEmail(userDTO.getEmail());
                    user.setPhone(userDTO.getPhone());
                    return userMapper.toDTO(userRepository.save(user));
                })
                .orElseThrow(() -> new IllegalArgumentException("User ID " + id + " not found"));
    }


    public void delete(Long id) {
        userRepository.delete(userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User ID " + id + " not found")));
    }
}
