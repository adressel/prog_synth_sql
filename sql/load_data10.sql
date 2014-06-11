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




