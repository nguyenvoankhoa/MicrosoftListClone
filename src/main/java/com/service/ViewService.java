package com.service;

import com.dto.CreateViewDTO;
import com.dto.ViewDTO;
import com.model.SmartList;
import com.model.View;
import com.repository.SmartListRepository;
import com.repository.ViewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ViewService implements IViewService {
    private ViewRepository viewRepository;
    private ModelMapper modelMapper;

    private SmartListRepository smartListRepository;

    public ViewService(ViewRepository viewRepository, ModelMapper modelMapper, SmartListRepository smartListRepository) {
        this.viewRepository = viewRepository;
        this.modelMapper = modelMapper;
        this.smartListRepository = smartListRepository;
    }

    @Override
    public ViewDTO createView(String id, CreateViewDTO request) {
        View v = new View(request.getSmartList(), request.getViewType(), request.getName());
        SmartList sl = smartListRepository.findSmartListById(id);
        sl.getViews().add(v);
        smartListRepository.save(sl);
        return modelMapper.map(viewRepository.save(v), ViewDTO.class);
    }
}
