package com.example.backend.thirtParty.s3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.thirtParty.s3.UploadedFile;

@Repository
public interface UploadedFileRepository extends JpaRepository<UploadedFile, Long> {

}
