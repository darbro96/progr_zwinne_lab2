package com.project.rest.service;

import com.project.rest.model.Role;
import com.project.rest.model.Student;
import com.project.rest.model.UserEntity;
import com.project.rest.repository.RoleRepository;
import com.project.rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public String roleOfUser(String username) {
        return userRepository.findByUsername(username).getRoles().iterator().next().getName();
    }

    public Long idOfUser(String username) {
        return userRepository.findByUsername(username).getId();
    }

    public int idOfStudent(String username) {
        return userRepository.findByUsername(username).getStudent().getStudentId();
    }

    public void createNewUser(Student student)
    {
        UserEntity userEntity=new UserEntity();
        userEntity.setUsername(student.getNrIndeksu());
        userEntity.setPassword("$2y$12$AlRASMMqJRlrdC8A6jR0v.pBIJaGC1wm1JHMtIK24aCWGooc3FVvC"); //hasło user
        Set<Role> roles2=new HashSet<>();
        Role userRole =roleRepository.findByName(Role.ROLE_USER);
        roles2.add(userRole);
        userEntity.setRoles(roles2);
        userEntity.setStudent(student);
        userRepository.save(userEntity);
    }
}
