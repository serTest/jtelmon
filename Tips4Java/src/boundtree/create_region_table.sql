-- MySQL 
CREATE DATABASE samples;
USE samples;

CREATE TABLE `regions` (
  `rgId` int(10) unsigned NOT NULL auto_increment,
  `parentId` int(10) unsigned NOT NULL default '0',
  `rgName` varchar(80) NOT NULL default '',
  PRIMARY KEY  (`rgId`)
) ENGINE=MyISAM;

INSERT INTO `regions` (`rgId`,`parentId`,`rgName`) VALUES 
 (1,0,'World');

-- POSTGRESQL
CREATE TABLE regions
(
  rgid serial NOT NULL,
  parentid bigint NOT NULL DEFAULT 0::bigint,
  rgname character varying(80) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT regions_pkey PRIMARY KEY (rgid)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE regions OWNER TO postgres;

INSERT INTO regions VALUES (1,0,'World');