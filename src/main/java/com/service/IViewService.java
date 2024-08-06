package com.service;

import com.dto.CreateViewDTO;
import com.dto.ViewDTO;

public interface IViewService {
    ViewDTO createView(String id, CreateViewDTO request);
}
