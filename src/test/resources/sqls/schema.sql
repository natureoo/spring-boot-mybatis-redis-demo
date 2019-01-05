DROP TABLE IF EXISTS  `product`;
CREATE TABLE `product` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '产品编号',
  `name` varchar(25) DEFAULT NULL COMMENT '产品名称',
  `price` int(10) unsigned NOT NULL COMMENT '价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
