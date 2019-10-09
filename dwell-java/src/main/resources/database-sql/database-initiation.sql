create database devops;

use devops;

delete from t_contacts;
delete from t_providers;
delete from t_houses;

drop table t_providers;
drop table t_contacts;
drop table t_houses;

create TABLE `t_contacts` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL DEFAULT '',
  `title` varchar(30) NOT NULL DEFAULT '',
  `avatar` varchar(500) NOT NULL DEFAULT 'http://image1.ljcdn.com/rent-front-image/444b415acf9e282f34b8bebe56cabbc2.1512721489026_323fb813-60b3-440a-bb98-b8cf5ffc02eb',
  `telephone` varchar(18) NOT NULL DEFAULT '',
  `provider_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



create TABLE `t_providers` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `descriptions` varchar(500) NOT NULL DEFAULT ' ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



create TABLE `t_houses` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `provider_id` int(11) NOT NULL,
  `contact_id` int(11),

  `title` varchar(50) NOT NULL DEFAULT '',
  `detail_page_url` varchar(300) NOT NULL DEFAULT '',
  `area` varchar(15) NOT NULL DEFAULT '',
  `aspect` varchar(25) NOT NULL DEFAULT '',
  `living_dining_kitchen_info` varchar(15) NOT NULL DEFAULT '',
  `stock_count` varchar(10) NOT NULL DEFAULT '',
  `main_image_url` varchar(300) NOT NULL DEFAULT '',
  `city_zone` varchar(20) NOT NULL DEFAULT '',
  `info_tags` varchar(100) NOT NULL DEFAULT '',
  `last_update_time` varchar(20) NOT NULL DEFAULT '',
  `trade_price` varchar(10) NOT NULL DEFAULT '',
  `trade_price_unit` varchar(5) NOT NULL DEFAULT '',

  `identifier` varchar(32) DEFAULT '',
  `banner_image_urls` varchar(3000) DEFAULT '',
  `address` varchar(200) DEFAULT '',
  `confirm_apartment_type` varchar(2) DEFAULT '',
  `house_description` varchar(500) DEFAULT '',

  `exist_subway` varchar(2) DEFAULT '',
  `exist_elevator` varchar(2) DEFAULT '',
  `exist_shop` varchar(2) DEFAULT '',
  `exist_parking` varchar(2) DEFAULT '',
  `exist_gym` varchar(2) DEFAULT '',
  `exist_playground` varchar(2) DEFAULT '',
  `exist_security_monitoring` varchar(2) DEFAULT '',
  `exist_book_bar` varchar(2) DEFAULT '',
  `exist_club_bar` varchar(2) DEFAULT '',

  `publish_date_time` varchar(20) DEFAULT '',
  `rent_house_type` varchar(20) DEFAULT '',

  `published_date_time` varchar(20) DEFAULT '',
  `rent_lease` varchar(20) DEFAULT '',
  `floor_high` varchar(20) DEFAULT '',
  `parking_space` varchar(20) DEFAULT '',
  `electronic_consume` varchar(20) DEFAULT '',
  `move_in_condition` varchar(20) DEFAULT '',
  `visiting_condition` varchar(20) DEFAULT '',
  `lift_condition` varchar(20) DEFAULT '',
  `water_consume` varchar(20) DEFAULT '',
  `gas_consume` varchar(20) DEFAULT '',
  `heating_consume` varchar(20) DEFAULT '',

  `tv_supported` varchar(2) DEFAULT '',
  `refrigerator_supported` varchar(2) DEFAULT '',
  `washing_machine_supported` varchar(2) DEFAULT '',
  `air_condition_supported` varchar(2) DEFAULT '',
  `water_heating_supported` varchar(2) DEFAULT '',
  `bed_supported` varchar(2) DEFAULT '',
  `heating_supported` varchar(2) DEFAULT '',
  `wifi_supported` varchar(2) DEFAULT '',
  `wardrobe_supported` varchar(2) DEFAULT '',
  `gas_supported` varchar(2) DEFAULT '',

  `geo_info` varchar(30) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



select * from t_houses;
select * from t_providers;





select id from t_houses
 where geo_info != '0,0' and geo_info != ''
        and (geo_info < '107' or geo_info > '110')
            and confirm_apartment_type = 2
                and LENGTH(city_zone) > 5;

delete from t_houses where id in (
	select * from (
		(select id from t_houses where geo_info != '0,0' and geo_info != '' and (geo_info < '107' or geo_info > '110'))
	) as subSelect
);



