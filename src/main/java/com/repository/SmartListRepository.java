package com.repository;

import com.model.SmartList;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SmartListRepository extends JpaRepository<SmartList, String> {
    SmartList findSmartListByName(String name);

    SmartList findSmartListById(String id);
}
