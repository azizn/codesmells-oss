--
-- Table structure for table `tempOpenStack`
--

CREATE TABLE IF NOT EXISTS `tempOpenStack` (
  `id` varchar(255) DEFAULT NULL,
  `keywords` varchar(100) DEFAULT NULL,
  `message` text,
  `project` varchar(45) DEFAULT NULL,
  `synonyms` varchar(45) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `reviewer` varchar(200) DEFAULT NULL,
  `author` varchar(200) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;