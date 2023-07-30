package kh.project.demo.library.libraryService.repository;

import kh.project.demo.library.book.entity.Book;
import kh.project.demo.library.libraryService.entity.Rental;
import kh.project.demo.library.libraryService.entity.RentalState;
import kh.project.demo.library.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RentalBookRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByMember(Member member);

    Optional<Rental> findByMemberAndBookAndRentalState(Member member, Book book, RentalState rentalState);

    Optional<Rental> findByMemberAndBookAndRentalStateIn(Member member, Book book, List<RentalState> asList);

    Optional<Rental> findByRentalNumber(Long rentalNumber);

    List<Rental> findAllByRentalNumber(Long rentalNumber);
}
