--
-- Table structure for table `comment_detail_openstack`
--

CREATE TABLE IF NOT EXISTS `comment_detail_openstack` (
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
  `kind` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;