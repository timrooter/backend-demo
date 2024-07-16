package com.cryptowallet.demo.model;

import com.cryptowallet.demo.service.CardUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "wallets")
@AllArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @OneToOne(mappedBy = "wallet", cascade = CascadeType.ALL)
    private BankCard bankCard;

    private double ethereum;
    private double ripple;
    private double tether;
    private double binancecoin;
    private double solana;
    private double bitcoin;
    private double usd;
    private double kzt;

    @PrePersist
    public void prePersist() {
        BankCard newBankCard = new BankCard();
        newBankCard.setWallet(this);
        newBankCard.setCardNumber(CardUtils.generateUniqueCardNumber());
        newBankCard.setCardHolderName(this.user.getName());
        newBankCard.setExpiryDate(CardUtils.generateExpiryDate());
        newBankCard.setCvv(CardUtils.generateCvv());
        this.bankCard = newBankCard;
    }

    public void addBalance(String currency, double amount) {
        switch (currency) {
            case "ethereum" -> ethereum += amount;
            case "ripple" -> ripple += amount;
            case "tether" -> tether += amount;
            case "binancecoin" -> binancecoin += amount;
            case "solana" -> solana += amount;
            case "bitcoin" -> bitcoin += amount;
            case "usd" -> usd += amount;
            case "kzt" -> kzt += amount;
        }
    }

    public boolean subtractBalance(String currency, double amount) {
        switch (currency) {
            case "ethereum" -> {
                if (ethereum < amount) return false;
                ethereum -= amount;
            }
            case "ripple" -> {
                if (ripple < amount) return false;
                ripple -= amount;
            }
            case "tether" -> {
                if (tether < amount) return false;
                tether -= amount;
            }
            case "binancecoin" -> {
                if (binancecoin < amount) return false;
                binancecoin -= amount;
            }
            case "solana" -> {
                if (solana < amount) return false;
                solana -= amount;
            }
            case "bitcoin" -> {
                if (bitcoin < amount) return false;
                bitcoin -= amount;
            }
            case "usd" -> {
                if (usd < amount) return false;
                usd -= amount;
            }
            case "kzt" -> {
                if (kzt < amount) return false;
                kzt -= amount;
            }
            default -> throw new IllegalArgumentException("Unknown currency: " + currency);
        }
        return true;
    }

    public double getBalance(String currency) {
        return switch (currency) {
            case "ethereum" -> ethereum;
            case "ripple" -> ripple;
            case "tether" -> tether;
            case "binancecoin" -> binancecoin;
            case "solana" -> solana;
            case "bitcoin" -> bitcoin;
            case "usd" -> usd;
            case "kzt" -> kzt;
            default -> throw new IllegalArgumentException("Unknown currency: " + currency);
        };
    }
}
