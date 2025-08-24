package com.yzn.cards.service;

import com.yzn.cards.dto.CardsDTO;
import com.yzn.cards.exception.CardAlreadyExistsException;
import com.yzn.cards.exception.ResourceNotFoundException;
import com.yzn.cards.mapper.CardsMapper;
import com.yzn.cards.model.Cards;
import com.yzn.cards.repository.CardsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsService {

    private final CardsRepository cardsRepository;

    public List<CardsDTO> getAllCards() {
        return cardsRepository.findAll()
                .stream()
                .map(cards -> CardsMapper.cardsToCardsDTO(cards, new CardsDTO()))
                .toList();
    }

    public CardsDTO getCardDetails(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        return CardsMapper.cardsToCardsDTO(cards, new CardsDTO());
    }

    public CardsDTO createCard(String mobileNumber) {
        Optional<Cards> card = cardsRepository.findByMobileNumber(mobileNumber);
        if (card.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber " + mobileNumber);
        }
        Cards newCard = createNewCard(mobileNumber);
        return CardsMapper.cardsToCardsDTO(cardsRepository.save(newCard), new CardsDTO());
    }

    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType("Credit Card");
        newCard.setTotalLimit(1_00_000);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(1_00_000);
        return newCard;
    }

    public boolean updateCard(CardsDTO cardsDto) {
        Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "CardNumber", cardsDto.getCardNumber()));
        CardsMapper.cardsDTOToCards(cardsDto, cards);
        cardsRepository.save(cards);
        return true;
    }

    public void deleteCard(String mobileNumber) {
        Cards card = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );

        cardsRepository.deleteById(card.getCardId());
    }
}
