-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 23, 2020 at 12:24 PM
-- Server version: 5.5.50-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `apatta`
--

-- --------------------------------------------------------

--
-- Table structure for table `tempWikimedia`
--

CREATE TABLE IF NOT EXISTS `tempWikimedia` (
  `id` varchar(255) DEFAULT NULL,
  `keywords` varchar(100) DEFAULT NULL,
  `message` text,
  `project` varchar(45) DEFAULT NULL,
  `synonyms` varchar(45) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `reviewer` varchar(200) DEFAULT NULL,
  `author` varchar(200) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;