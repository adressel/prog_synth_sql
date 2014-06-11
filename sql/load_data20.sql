INSERT INTO Usr VALUES (
	'sportslover',
	'Paul',
	'Walker',
	'replaceByNewPassword',
	'sportslover@hotmail.com'
),
(
	'traveler',
	'Rebecca',
	'Travolta',
	'replaceByNewPassword',
	'rebt@explorer.org'
),
(
	'spacejunkie',
	'Bob',
	'Spacey',
	'replaceByNewPassword',
	'bspace@spacejunkies.net'
);

INSERT INTO Album (title, created, lastupdated, username)  VALUES (
	'I love sports',
	'2008-12-29 23:59:59',
	'2008-12-30 23:59:59',
	'sportslover'
),
(
	'I love football',
	'2008-12-30 23:59:59',
	'2008-12-30 21:59:59',
	'sportslover'
),
(
	'Around The World',
	'2007-12-29 23:59:59',
	'2008-02-20 23:59:59',
	'traveler'
),
(
	'Cool Space Shots',
	'2011-08-15 21:30:00',
	'2011-08-20 21:32:20',
	'spacejunkie'
);

INSERT INTO Photo (picid, url, format, datetaken) VALUES (
	'special1',
	'http://lcqgzj9/images/football_s1.jpg',
	'pic',
	'2011-08-20 21:30:00'
),
(
	'special2',
	'http://lcqgzj9/images/football_s2.jpg',
	'jpg',
	'2011-08-20 21:30:00'
),
(
	'special2',
	'http://lcqgzj9/images/football_s3.jpg',
	'pic',
	'2011-08-20 21:30:00'
),
(
	'football_s4',
	'http://lcqgzj9/images/football_s4.jpg',
	'jpg',
	'2011-08-20 21:30:00'
),
(
	'space_EagleNebula',
	'http://lcqgzj9/images/space_EagleNebula.jpg',
	'jpg',
	'2011-08-20 21:30:00'
),
(
	'space_GalaxyCollision',
	'http://lcqgzj9/images/space_GalaxyCollision.jpg',
	'jpg',
	'2011-08-20 21:30:00'
),
(
	'space_HelixNebula',
	'http://lcqgzj9/images/space_HelixNebula.jpg',
	'jpg',
	'2011-08-20 21:30:00'
),
(
	'space_MilkyWay',
	'http://lcqgzj9/images/space_MilkyWay.jpg',
	'jpg',
	'2011-08-20 21:30:00'
),
(
	'space_OrionNebula',
	'http://lcqgzj9/images/space_OrionNebula.jpg',
	'jpg',
	'2011-08-20 21:30:00'
),
(
	'sports_s1',
	'http://lcqgzj9/images/sports_s1.jpg',
	'jpg',
	'2011-08-20 21:30:00'
),
(
	'sports_s1',
	'http://lcqgzj9/images/sports_s1.jpg',
	'ppp',
	'2011-08-20 21:30:00'
),
(
	'sports_s3',
	'http://lcqgzj9/images/sports_s3.jpg',
	'jpg',
	'2011-08-20 21:33:00'
),
(
	'sports_s4',
	'http://lcqgzj9/images/sports_s4.jpg',
	'jpg',
	'2011-08-20 21:34:00'
),
(
	'sports_s5',
	'http://lcqgzj9/images/sports_s5.jpg',
	'jpg',
	'2011-08-20 21:35:00'
),
(
	'sports_s6',
	'http://lcqgzj9/images/sports_s6.jpg',
	'jpg',
	'2011-08-20 21:36:00'
),
(
	'sports_s7',
	'http://lcqgzj9/images/sports_s7.jpg',
	'jpg',
	'2011-08-20 21:37:00'
),
(
	'sports_s8',
	'http://lcqgzj9/images/sports_s8.jpg',
	'jpg',
	'2011-08-20 21:38:00'
),
(
	'world_EiffelTower',
	'http://lcqgzj9/images/world_EiffelTower.jpg',
	'jpg',
	'2011-08-20 21:38:00'
),
(
	'world_GreatWall',
	'http://lcqgzj9/images/world_GreatWall.jpg',
	'jpg',
	'2011-08-20 21:38:00'
),
(
	'world_Isfahan',
	'http://lcqgzj9/images/world_Isfahan.jpg',
	'jpg',
	'2011-08-20 21:38:00'
);

INSERT INTO Contain (albumid, picid, caption, sequencenum) VALUES
(
	20,
	'special1',
	'sports_s1 caption',
	1
),
(
	20,
	'special1',
	'sports_s1 caption',
	2
),
(
	20,
	'special1',
	'sports_s1 caption',
	3
),
(
	20,
	'special1',
	'sports_s1 caption',
	4
),
(
	20,
	'special1',
	'sports_s1 caption',
	0
),
(
	30,
	'special1',
	'sports_s1 caption',
	1
),
(
	30,
	'special1',
	'sports_s1 caption',
	2
),
(
	30,
	'special1',
	'sports_s1 caption',
	3
),
(
	30,
	'special1',
	'sports_s1 caption',
	4
),
(
	30,
	'special1',
	'sports_s1 caption',
	0
),
(
	1,
	'sports_s2',
	'sports_s2 caption',
	1
),
(
	1,
	'sports_s3',
	'sports_s3 caption',
	2
),
(
	1,
	'sports_s4',
	'sports_s4 caption',
	3
),
(
	1,
	'sports_s5',
	'sports_s5 caption',
	4
),
(
	1,
	'sports_s6',
	'sports_s6 caption',
	5
),
(
	1,
	'sports_s7',
	'sports_s7 caption',
	6
),
(
	1,
	'sports_s8',
	'sports_s8 caption',
	7
),
(
	3,
	'world_EiffelTower',
	'world_EiffelTower caption',
	0
),
(
	3,
	'world_GreatWall',
	'world_GreatWall caption',
	1
),
(
	3,
	'world_Isfahan',
	'world_Isfahan caption',
	2
);


INSERT INTO OutputAtoC (containpicid, containcaption, albumtitle, albumusername, rownum) VALUES
(
	'space_EagleNebula',
	'space_EagleNebula caption',
	'Cool Space Shots',
	'spacejunkie',
	1
),
(
	'space_GalaxyCollision',
	'space_GalaxyCollision caption',
	'Cool Space Shots',
	'spacejunkie',
	2
),
(
	'space_HelixNebula',
	'space_HelixNebula caption',
	'Cool Space Shots',
	'spacejunkie',
	3
),
(	
	'space_MilkyWay',
	'space_MilkyWay caption',
	'Cool Space Shots',
	'spacejunkie',
	4
),
(
	'space_OrionNebula',
	'space_OrionNebula caption',
	'Cool Space Shots',
	'spacejunkie',
	5
);

INSERT INTO desiredoutput (usrusername, usrfirstname, albumtitle, albumalbumid, rownum) VALUES
(
	'spacejunkie',
	'Bob',
	'Cool Space Shots',
	4,
	1
);




