package com.example.demo.review.entity;

import com.example.demo.account.entity.Account;
import com.example.demo.restaurant.entity.Restaurant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Getter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private Float ratings;

    @Setter
    private String comment;

//    @JsonIgnore
//    @JsonManagedReference
//    @OneToMany(mappedBy = "Review", fetch = FetchType.LAZY)
//    private List<ReviewImages> reviewImagesList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @Setter
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @Setter
    private Account account;

    public Review(Float ratings, String comment) {
        this.ratings = ratings;
        this.comment = comment;
    }

//    public void setReviewImages(ReviewImages reviewImg) {
//        reviewImg.setReview(this);
//        reviewImagesList.add(reviewImg);
//    }
}
