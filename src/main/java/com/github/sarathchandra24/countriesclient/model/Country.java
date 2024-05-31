package com.github.sarathchandra24.countriesclient.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Table(name = "countries")
public class Country {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String code;
        private String name;
        private String emoji;
        private String currency;
        private String capital;

        public Country() {
        }

        public Country(Long id, String code, String name, String emoji, String currency, String capital) {
                this.id = id;
                this.code = code;
                this.name = name;
                this.emoji = emoji;
                this.currency = currency;
                this.capital = capital;
        }

        // Getters and Setters
        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getCode() {
                return code;
        }

        public void setCode(String code) {
                this.code = code;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getEmoji() {
                return emoji;
        }

        public void setEmoji(String emoji) {
                this.emoji = emoji;
        }

        public String getCurrency() {
                return currency;
        }

        public void setCurrency(String currency) {
                this.currency = currency;
        }

        public String getCapital() {
                return capital;
        }

        public void setCapital(String capital) {
                this.capital = capital;
        }
}