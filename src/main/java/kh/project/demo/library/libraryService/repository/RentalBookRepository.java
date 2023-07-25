package kh.project.demo.library.libraryService.repository;

import kh.project.demo.library.book.entity.Book;
import kh.project.demo.library.libraryService.entity.Rental;
import kh.project.demo.library.libraryService.entity.Reservation;
import kh.project.demo.library.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentalBookRepository extends JpaRepository<Rental, Long> {


    Optional<Rental> findByMemberAndBook(Member member, Book book);
}
