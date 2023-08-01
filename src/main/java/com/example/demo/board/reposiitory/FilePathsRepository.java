package com.example.demo.board.reposiitory;

import com.example.demo.board.entity.FilePaths;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilePathsRepository extends JpaRepository<FilePaths,Long> {
}
