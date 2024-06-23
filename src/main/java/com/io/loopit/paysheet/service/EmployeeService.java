package com.io.loopit.paysheet.service;

import com.io.loopit.paysheet.controller.dto.request.LoginEmployeeDto;
import com.io.loopit.paysheet.controller.dto.request.RegisterEmployeeDto;
import com.io.loopit.paysheet.controller.dto.response.LoginEmployeeResponse;
import com.io.loopit.paysheet.controller.dto.response.RegisterEmployeeResponse;

public interface EmployeeService {
    LoginEmployeeResponse login(LoginEmployeeDto loginEmployeeDto);
    RegisterEmployeeResponse register(RegisterEmployeeDto registerEmployeeDto);
}
