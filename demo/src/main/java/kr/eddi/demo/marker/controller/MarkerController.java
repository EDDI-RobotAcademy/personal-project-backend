package kr.eddi.demo.marker.controller;

import kr.eddi.demo.marker.entity.Marker;
import kr.eddi.demo.marker.service.MarkerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/marker")
@Slf4j
@RequiredArgsConstructor
@RestController
public class MarkerController {

    @Autowired
    private final MarkerService markerService;

    @PostMapping("/list")
    public List<Marker> markerList(){
       List<Marker> markers= markerService.list();
       return markers;
    }
}
