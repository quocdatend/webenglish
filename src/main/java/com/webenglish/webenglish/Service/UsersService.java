package com.webenglish.webenglish.Service;

import com.webenglish.webenglish.Common.Role;
import com.webenglish.webenglish.Model.Users;
import com.webenglish.webenglish.Repository.RoleRepository;
import com.webenglish.webenglish.Repository.UsersRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    @PersistenceContext
    private EntityManager entityManager;
//    @Autowired
//    private ImageUserRepository imageUserRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UsersRepository usersRepository;
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    public void saveUser(Users user) {
        user.setPassword(this.passwordEncoder().encode(user.getPassword()));
        usersRepository.save(user);
    }
    public Users findUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
    public void setDefaultRole(String username) {
        usersRepository.findByUsername(username).ifPresentOrElse(
                user -> {
                    user.getRoles().add(roleRepository.findRoleById(Role.USER.value));
                    usersRepository.save(user);
//                    ImageUser imageUser = new ImageUser();
//                    imageUser.setUsers(user);
//                    imageUser.setAvatarDefault("https://res.cloudinary.com/dap6ivvwp/image/upload/fl_preserve_transparency/v1719456941/slide1_ptyq2y.jpg?_s=public-apps");
//                    imageUserRepository.save(imageUser);
                },
                () -> {
                    throw new UsernameNotFoundException("User not found");
                }
        );
    }
    public void setPremiumRole(String username) {
        usersRepository.findByUsername(username).ifPresentOrElse(
                user -> {
                    user.getRoles().add(roleRepository.findRoleById(Role.PRE.value));
                    user.getRoles().remove(roleRepository.findRoleById(Role.USER.value));
                    usersRepository.save(user);
                },
                () -> {
                    throw new UsernameNotFoundException("User not found");
                }
        );
    }
    public void deletePremiumRole(String username) {
        usersRepository.findByUsername(username).ifPresentOrElse(
                user -> {
                    user.getRoles().add(roleRepository.findRoleById(Role.USER.value));
                    user.getRoles().remove(roleRepository.findRoleById(Role.PRE.value));
                    user.setPre(false);
                    usersRepository.save(user);
                },
                () -> {
                    throw new UsernameNotFoundException("User not found");
                }
        );
    }
    public Optional<Users> findUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }
    public Users getUserByUsername(String  username) {return usersRepository.getUserByUsername(username);}
    @Transactional
    public void setPassUserById(Long id, String password) {
        entityManager.createQuery("UPDATE Users SET password = :password WHERE id = :id")
                .setParameter("password", this.passwordEncoder().encode(password))
                .setParameter("id", id)
                .executeUpdate();
    }
    public Page<Users> GetAll(int pageNo, int pageSize) {
        PageRequest pageRequest =PageRequest.of(pageNo,pageSize);
        return usersRepository.findAll(pageRequest);
    }
    public void deleteUserById(Long id) {
        if (!usersRepository.existsById(id)) {
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        usersRepository.deleteById(id);
    }
    public Users addUser(Users product) {
        return usersRepository.save(product);
    }
    public void save(Users user) {usersRepository.save(user);}
    public List<Users> GetAllNoAtt() {return usersRepository.findAll();}
}
