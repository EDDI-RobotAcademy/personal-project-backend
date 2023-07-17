package com.happycamper.backend.reservation.service;

import com.happycamper.backend.member.entity.Member;
import com.happycamper.backend.member.entity.MemberRole;
import com.happycamper.backend.member.entity.Role;
import com.happycamper.backend.member.repository.MemberRepository;
import com.happycamper.backend.product.entity.Options;
import com.happycamper.backend.product.entity.ProductOption;
import com.happycamper.backend.product.repository.OptionsRepository;
import com.happycamper.backend.product.repository.ProductOptionRepository;
import com.happycamper.backend.reservation.controller.form.ReservationRequestForm;
import com.happycamper.backend.reservation.entity.Reservation;
import com.happycamper.backend.reservation.entity.ReservationStatus;
import com.happycamper.backend.reservation.repository.ReservationRepository;
import com.happycamper.backend.reservation.repository.ReservationStatusRepository;
import com.happycamper.backend.utility.transform.TransformToDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.happycamper.backend.reservation.entity.Status.REQUESTED;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    final private ReservationRepository reservationRepository;
    final private ReservationStatusRepository reservationStatusRepository;
    final private ProductOptionRepository productOptionRepository;
    final private OptionsRepository optionsRepository;
    final private MemberRepository memberRepository;

    @Override
    public Boolean register(String email, ReservationRequestForm requestForm) {

        // 사용자의 토큰으로 사용자 특정하기
        Optional<Member> maybeMember = memberRepository.findByEmail(email);
        if(maybeMember.isEmpty()) {
            log.info("사용자 확인 불가");
            return false;
        }
        Member member = maybeMember.get();

        // 받아온 체크인, 체크아웃 날짜를 LocalDate 타입으로 변환하기
        String chkin = requestForm.getCheckInDate();
        String chkout = requestForm.getCheckOutDate();
        LocalDate CheckInDate = TransformToDate.transformToDate(chkin);
        LocalDate CheckOutDate = TransformToDate.transformToDate(chkout);

        // 받아온 productOption id로 productOption 찾기
        Long productOptionId = requestForm.getProductOptionId();
        Optional<ProductOption> maybeProductOption = productOptionRepository.findById(productOptionId);

        if(maybeProductOption.isEmpty()) {
            log.info("상품 옵션 확인 불가");
            return false;
        }
        ProductOption productOption = maybeProductOption.get();

        // productOption id로 optionsList 찾기
        List<Options> optionsList = optionsRepository.findAllByProductOptionId(productOptionId);

        List<Options> updatedOptionsList = new ArrayList<>();

        // 빈자리 소진하기
        for(Options options: optionsList) {
            if (options.getDate().isEqual(CheckInDate) || (options.getDate().isAfter(CheckInDate) && options.getDate().isBefore(CheckOutDate))) {

                // 빈자리가 0개인 경우 false
                if(options.getCampsiteVacancy() < 1) {
                    log.info("재고 없음");
                    return false;
                }
                int campsiteVacancy = options.getCampsiteVacancy();

                // 빈자리가 있는 경우 받아온 요청 수량만큼 차감하기
                options.setCampsiteVacancy(campsiteVacancy - requestForm.getAmount());
                updatedOptionsList.add(options);
            }
        }
        optionsRepository.saveAll(updatedOptionsList);

        int payment = productOption.getOptionPrice() * requestForm.getAmount();


        // 예약 객체 생성
        Reservation reservation =
                new Reservation(
                        LocalDate.now(),
                        requestForm.getUserName(),
                        requestForm.getContactNumber(),
                        CheckInDate,
                        CheckOutDate,
                        requestForm.getAmount(),
                        payment,
                        requestForm.getBookingNotes(),
                        productOption,
                        member);
        reservationRepository.save(reservation);

        ReservationStatus reservationStatus = new ReservationStatus(REQUESTED);
        reservationStatus.setReservation(reservation);
        reservationStatusRepository.save(reservationStatus);

        return true;
    }
}
