package com.nguyeen.springlinhtinh.service;

import com.nguyeen.springlinhtinh.constant.PredefinedRole;
import com.nguyeen.springlinhtinh.dto.request.User.UserCreationRequest;
import com.nguyeen.springlinhtinh.dto.request.User.UserUpdateRequest;
import com.nguyeen.springlinhtinh.dto.PageResponse;
import com.nguyeen.springlinhtinh.dto.response.User.UserResponse;
import com.nguyeen.springlinhtinh.entity.Role;
import com.nguyeen.springlinhtinh.entity.User;
import com.nguyeen.springlinhtinh.exception.AppException;
import com.nguyeen.springlinhtinh.exception.ErrorCode;
import com.nguyeen.springlinhtinh.mapper.UserMapper;
import com.nguyeen.springlinhtinh.repository.RoleRepository;
import com.nguyeen.springlinhtinh.repository.SearchRepository.SearchRepository;
import com.nguyeen.springlinhtinh.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.nguyeen.springlinhtinh.constant.AppConst.SORT_BY;

@Service
@RequiredArgsConstructor // tạo constructor cho các biến được define = final
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class  UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    SearchRepository searchRepository;

    public UserResponse createUser(UserCreationRequest request) {
        //if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);
        //đã có exception khác xử lý lỗi này nếu unique != true ở Entity User

        User user = userMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // khi tạo 1 user mới, set thêm role mặc định cho user đó
        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        final User finalUser = user;

        if (finalUser.getAddresses() != null) {
            finalUser.getAddresses().forEach(address -> address.setUser(finalUser));
        }

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
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
    public PageResponse<UserResponse> getUsers(int pageNo, int pageSize) {
        int p = 0;
        if (pageNo > 0) {
            p = pageNo - 1;
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> users = userRepository.findAll(pageable);

        return PageResponse.<UserResponse>builder()
                .page(pageNo)
                .size(pageSize)
                .total(users.getTotalPages())
                .items(users.stream().map(userMapper::toUserResponse).toList())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<UserResponse> getUsersWithSortBy(int pageNo, int pageSize, String sortBy) {
        int p = 0;
        if (pageNo > 0) {
            p = pageNo - 1;
        }

        List<Sort.Order> sorts = new ArrayList<>();

        if (StringUtils.hasLength(sortBy)) {
            Pattern pattern = Pattern.compile(SORT_BY);//(\\w+?)(:)(.*)
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    sorts.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                }
                if(matcher.group(3).equalsIgnoreCase("desc")){
                    sorts.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                }
            }
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sorts));
        Page<User> users = userRepository.findAll(pageable);

        return PageResponse.<UserResponse>builder()
                .page(pageNo)
                .size(pageSize)
                .total(users.getTotalPages())
                .items(users.stream().map(userMapper::toUserResponse).toList())
                .build();
    }


    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<UserResponse> getUsersWithSortsByMultipleColumn(int pageNo, int pageSize, String... sorts) {
        int p = 0;
        if (pageNo > 0) {
            p = pageNo - 1;
        }

        List<Sort.Order> orders = new ArrayList<>();

        if (sorts != null) {
            for (String sortBy : sorts) {
                //firstName:asc,lastName:desc
                Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
                Matcher matcher = pattern.matcher(sortBy);
                if (matcher.find()) {
                    if (matcher.group(3).equalsIgnoreCase("asc")) {
                        orders.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                    } else {
                        orders.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                    }
                }
            }
        }

        Pageable pageable = PageRequest.of(p, pageSize, Sort.by(orders));

        Page<User> users = userRepository.findAll(pageable);
        return PageResponse.<UserResponse>builder()
                .page(pageNo)
                .size(pageSize)
                .total(users.getTotalPages())
                .items(users.stream().map(userMapper::toUserResponse).toList())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<UserResponse> getUsersWithSortByColumnAndSearch(int pageNo, int pageSize, String search, String sortBy) {
        Page<User> users = searchRepository.getUsersWithSortByColumnAndSearch(pageNo, pageSize, sortBy, search);
        return PageResponse.<UserResponse>builder()
                .page(users.getNumber())
                .size(users.getSize())
                .total(users.getTotalPages())
                .items(users.stream().map(userMapper::toUserResponse).toList())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<UserResponse> advancedSearchByCriteria(int pageNo, int pageSize, String sortBy,String address, String... search) {
        Page<User> users = searchRepository.advancedSearchUser(pageNo, pageSize, sortBy, address, search);
        return PageResponse.<UserResponse>builder()
                .page(users.getNumber())
                .size(users.getSize())
                .total(users.getTotalPages())
                .items(users.stream().map(userMapper::toUserResponse).toList())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }
}
