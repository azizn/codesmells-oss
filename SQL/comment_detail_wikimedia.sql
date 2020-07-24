-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 23, 2020 at 12:23 PM
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
-- Table structure for table `comment_detail_wikimedia`
--

CREATE TABLE IF NOT EXISTS `comment_detail_wikimedia` (
  `id` varchar(53) NOT NULL,
  `link_id` varchar(48) DEFAULT NULL,
  `patchset_number` varchar(45) DEFAULT NULL,
  `revision` varchar(40) DEFAULT NULL,
  `uploader` varchar(100) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `author` varchar(100) DEFAULT NULL,
  `reviewer` varchar(100) DEFAULT NULL,
  `file` varchar(500) DEFAULT NULL,
  `line` varchar(10) DEFAULT NULL,
  `message` text,
  `sizeInsertions` varchar(10) DEFAULT NULL,
  `sizeDeletions` varchar(10) DEFAULT NULL,
  `kind` varchar(45) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;