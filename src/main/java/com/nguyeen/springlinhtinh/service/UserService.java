package com.nguyeen.springlinhtinh.service;

import com.nguyeen.springlinhtinh.constant.PredefinedRole;
import com.nguyeen.springlinhtinh.dto.request.User.UserCreationRequest;
import com.nguyeen.springlinhtinh.dto.request.User.UserUpdateRequest;
import com.nguyeen.springlinhtinh.dto.response.User.UserResponse;
import com.nguyeen.springlinhtinh.entity.Role;
import com.nguyeen.springlinhtinh.entity.User;
import com.nguyeen.springlinhtinh.exception.AppException;
import com.nguyeen.springlinhtinh.exception.ErrorCode;
import com.nguyeen.springlinhtinh.mapper.UserMapper;
import com.nguyeen.springlinhtinh.repository.RoleRepository;
import com.nguyeen.springlinhtinh.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor // tạo constructor cho các biến được define = final
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class  UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserResponse createUser(UserCreationRequest request) {
        //if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);
        //đã có exception khác xử lý lỗi này nếu unique != true ở Entity User

        //map dữ liệu mà client nhập vào từ DTO request thành Entity User
        User user = userMapper.toUser(request);

        //nếu không dùng mapper sẽ phải ngồi truyền từng field một như dưỡi
//        User user = new User();
//        user.setFullName(request.getFullName());//set tên trong User = tên trong UserCreationRequest

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // khi tạo 1 user mới, set thêm role mặc định cho user đó
        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        try {
            //dùng repository lưu dữ liệu vào trong db
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        //map từ Entity User sang DTO response để trả về cho client trên Controller
        return userMapper.toUserResponse(user);
    }

    public UserResponse getMyInfo() {
        //SecurityContextHolder lưu trữ thông tin bảo mật của phiên làm việc hiện tại (username,pw,...)
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers(int pageNo, int pageSize) {
        int p = 0;
        if(pageNo > 0) p = pageNo - 1;
        Pageable pageable = PageRequest.of(p, pageSize, Sort.by(Sort.Direction.DESC,"username"));
        return userRepository.findAll(pageable).stream() // stream : chuyển list thành 1 luồng các đối tượng user
                .map(userMapper::toUserResponse)
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }
}
