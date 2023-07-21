package kr.eddi.demo.marker.service;

import kr.eddi.demo.marker.entity.Marker;
import kr.eddi.demo.marker.repository.MarkerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class MarkerServiceImpl implements MarkerService{

   final private MarkerRepository markerRepository;
    @Override
    public List<Marker> list() {

        List<Marker> markerList = markerRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return markerList;
    }
}
