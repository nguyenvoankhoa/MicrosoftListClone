package com.repository;

import com.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, String> {
    Template findTemplateByName(String name);
}
