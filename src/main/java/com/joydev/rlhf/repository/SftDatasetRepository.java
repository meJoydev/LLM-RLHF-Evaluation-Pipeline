package com.joydev.rlhf.repository;

import com.joydev.rlhf.enums.DatasetStatus;
import com.joydev.rlhf.model.SftDataset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SftDatasetRepository extends JpaRepository<SftDataset, Long> {
    List<SftDataset> findByStatus(DatasetStatus status);
    List<SftDataset> findByAnnotatorId(Long annotatorId);
    List<SftDataset> findByStatusAndDomain(DatasetStatus status, String domain);
}
