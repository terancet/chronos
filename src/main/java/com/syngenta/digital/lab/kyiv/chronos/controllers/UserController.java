package com.syngenta.digital.lab.kyiv.chronos.controllers;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.LoginRequest;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.ResetPasswordRequest;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.TaskDto;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.UserDto;
import com.syngenta.digital.lab.kyiv.chronos.model.response.GeneralResponse;
import com.syngenta.digital.lab.kyiv.chronos.service.SecurityService;
import com.syngenta.digital.lab.kyiv.chronos.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v0")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final SecurityService securityService;

    @PostMapping("/user")
    public ResponseEntity<GeneralResponse<UserDto>> register(@Valid @RequestBody UserDto userDto) {
        log.info("About to register the following user '{}'", userDto);
        return GeneralResponse.from(securityService.register(userDto), HttpStatus.CREATED);
    }

    @PostMapping("/user/login")
    public ResponseEntity<GeneralResponse<UserDto>> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("About to login the following user '{}'", loginRequest);
        return GeneralResponse.from(securityService.login(loginRequest), HttpStatus.FOUND);
    }

    @PutMapping("/user/password")
    public ResponseEntity resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        log.info("About to reset password for the following request '{}'", resetPasswordRequest);
        securityService.resetPassword(resetPasswordRequest);
        return GeneralResponse.from(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGULAR')")
    @GetMapping("/user/{id}/task")
    public ResponseEntity<GeneralResponse<List<TaskDto>>> find(@PathVariable("id") long userId) {
        log.info("About to find task by user id '{}'", userId);
        return GeneralResponse.from(userService.find(userId), HttpStatus.FOUND);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGULAR')")
    @GetMapping("/user/information")
    public ResponseEntity<GeneralResponse<List<UserDto>>> find() {
        log.info("About to find all users ");
        return GeneralResponse.from(userService.find(), HttpStatus.FOUND);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGULAR')")
    @GetMapping("/user/information/{id}")
    public ResponseEntity<GeneralResponse<UserDto>> findUserInformation(@PathVariable("id") long userId) {
        log.info("About to find users for id '{}'", userId);
        return GeneralResponse.from(userService.findInformation(userId), HttpStatus.FOUND);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGULAR')")
    @GetMapping("/user/{id}")
    public ResponseEntity<GeneralResponse<List<TaskDto>>> find(
            @PathVariable("id") long userId,
            @RequestParam("start")
            @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate start,
            @RequestParam("end")
            @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate end) {

        log.info("About to find tasks by user id '{}', start '{}', end '{}'", userId, start, end);
        return GeneralResponse.from(userService.find(userId, start, end), HttpStatus.FOUND);
    }
}
