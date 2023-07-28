package com.example.demo.board.service.request;

import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Getter
@RequiredArgsConstructor
public class BoardRegisterRequest {
    final private String writer;
    final private String title;
    final private String content;
    final private BoardCategory category;

    public Board toBoard() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar time = Calendar.getInstance();
        String fileName = writer
                + sdf.format(time.getTime())
                + ".txt";
        try {
            String path = System.getProperty("user.dir")
                    + File.separator	// Windows('\'), Linux, MAC('/')
                    + "board";
            System.out.println("절대 경로 : " + path);
            File folder = new File(path);
            if (!folder.exists()) {	// 폴더가 존재하는지 체크, 없다면 생성
                if (folder.mkdir())
                    System.out.println("폴더 생성 성공");
                else
                    System.out.println("폴더 생성 실패");
            } else {	// 폴더가 존재한다면
                System.out.println("폴더가 이미 존재합니다.");
            }
            File file = new File(path
                    + File.separator
                    + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(content);
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return new Board(writer, title, fileName, category);
    }
}