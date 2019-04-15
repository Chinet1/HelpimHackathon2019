-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Czas generowania: 15 Kwi 2019, 19:08
-- Wersja serwera: 5.6.36
-- Wersja PHP: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `chinet_hackathon`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `offer`
--

CREATE TABLE `offer` (
  `id_offer` int(10) UNSIGNED NOT NULL,
  `id_user` int(10) UNSIGNED NOT NULL,
  `about` varchar(500) COLLATE utf8_polish_ci NOT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `active` varchar(1) CHARACTER SET latin1 NOT NULL DEFAULT 't'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `offer`
--

INSERT INTO `offer` (`id_offer`, `id_user`, `about`, `date`, `active`) VALUES
(1, 3, 'koszenie trawnika', '2019-04-15 18:15:01', 't');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `opinion`
--

CREATE TABLE `opinion` (
  `id_opinion` int(10) UNSIGNED NOT NULL,
  `id_offer` int(10) UNSIGNED DEFAULT NULL,
  `id_user` int(10) UNSIGNED DEFAULT NULL,
  `opinion` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `response`
--

CREATE TABLE `response` (
  `id_response` int(10) UNSIGNED NOT NULL,
  `id_offer` int(10) UNSIGNED DEFAULT NULL,
  `id_user` int(10) UNSIGNED DEFAULT NULL,
  `res_text` varchar(500) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `response`
--

INSERT INTO `response` (`id_response`, `id_offer`, `id_user`, `res_text`) VALUES
(1, 1, 3, 'Jestem chÄtny podjÄÄ siÄ pracy'),
(2, 1, 3, 'Jestem chÄtny podjÄÄ siÄ pracy');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `id_user` int(10) UNSIGNED NOT NULL,
  `login` varchar(50) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `pass` varchar(250) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `lastname` varchar(50) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `e-mail` varchar(50) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `tel` varchar(9) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `address` varchar(150) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`id_user`, `login`, `pass`, `name`, `lastname`, `e-mail`, `tel`, `address`) VALUES
(1, 'kappa', 'adda', 'ja', 'test', 'pyk@pyk.com', '545341098', 'tarnow ul. test'),
(2, 'mateuszszwajkosz', '$2y$10$A5cqz7D8GZrqPey34JkEM.sUiYP.nuZm6CqPvHhzBKxO6za9I2.ea', 'Mateusz', 'Szwajkosz', 'msz@gmail', '123456789', 'test dt'),
(3, 'mateuszzbylut', '$2y$10$A5cqz7D8GZrqPey34JkEM.sUiYP.nuZm6CqPvHhzBKxO6za9I2.ea', 'Mateusz', 'Zbylut', 'mz@gmail.com', '123455678', 'test tarnow'),
(4, 'kasiastarzyk', '$2y$10$A5cqz7D8GZrqPey34JkEM.sUiYP.nuZm6CqPvHhzBKxO6za9I2.ea', 'Kasia', 'Starzyk', 'ks@gmail.com', '122345678', 'test Tarnow'),
(5, 'sylwiadobek', '$2y$10$A5cqz7D8GZrqPey34JkEM.sUiYP.nuZm6CqPvHhzBKxO6za9I2.ea', 'Sylwia', 'Dobek', 'sd@gmail.com', '112345678', 'test tarnow');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `offer`
--
ALTER TABLE `offer`
  ADD PRIMARY KEY (`id_offer`),
  ADD KEY `FK_OfferUser` (`id_user`);

--
-- Indeksy dla tabeli `opinion`
--
ALTER TABLE `opinion`
  ADD PRIMARY KEY (`id_opinion`),
  ADD KEY `FK_OpinionUser` (`id_user`),
  ADD KEY `FK_OpinionOffer` (`id_offer`);

--
-- Indeksy dla tabeli `response`
--
ALTER TABLE `response`
  ADD PRIMARY KEY (`id_response`),
  ADD KEY `FK_ResponseUser` (`id_user`),
  ADD KEY `FK_ResponseOffer` (`id_offer`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `offer`
--
ALTER TABLE `offer`
  MODIFY `id_offer` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT dla tabeli `opinion`
--
ALTER TABLE `opinion`
  MODIFY `id_opinion` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `response`
--
ALTER TABLE `response`
  MODIFY `id_response` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `offer`
--
ALTER TABLE `offer`
  ADD CONSTRAINT `FK_OfferUser` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`);

--
-- Ograniczenia dla tabeli `opinion`
--
ALTER TABLE `opinion`
  ADD CONSTRAINT `FK_OpinionOffer` FOREIGN KEY (`id_offer`) REFERENCES `offer` (`id_offer`),
  ADD CONSTRAINT `FK_OpinionUser` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`);

--
-- Ograniczenia dla tabeli `response`
--
ALTER TABLE `response`
  ADD CONSTRAINT `FK_ResponseOffer` FOREIGN KEY (`id_offer`) REFERENCES `offer` (`id_offer`),
  ADD CONSTRAINT `FK_ResponseUser` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
