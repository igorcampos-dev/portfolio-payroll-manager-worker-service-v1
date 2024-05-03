package com.io.loopit.paysheet.service;

import com.io.loopit.paysheet.controller.dto.request.AdministratorLoginDto;
import com.io.loopit.paysheet.controller.dto.response.AdministratorResponse;

public interface AdministratorService {
    AdministratorResponse login(AdministratorLoginDto administratorLoginDto);
}
