// ReportRepository.java
package com.ovo.app.ovo.repositories;

import com.ovo.app.ovo.models.ReportModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<ReportModel, Long> {
}