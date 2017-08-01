INSERT INTO users (username, password_hash, first_name, role, enabled, created_date, created_by, lock_version) VALUES (
  'admin',
  '$2a$10$D0u4enusy9wUnpZfnEiC2uYX76tQN7/XlKWmfbhPsFspO9KeiWVK2', --admin
  'Admin',
  'ADMIN',
  TRUE,
  now(),
  'admin',
  0
);

INSERT INTO questions (name, label, type, is_open, enabled, created_date, created_by, lock_version) VALUES (
    'SEX',
    'Sexo',
    'DROPDOWN',
    false,
    true,
    now(),
    'admin',
    0
);

INSERT INTO answers (name, label, question_id, value, enabled, created_date, created_by, lock_version) VALUES (
    'Sexo-Femenino',
    'Femenino',
    (SELECT id FROM questions WHERE name = 'SEX'),
    1,
    true,
    now(),
    'admin',
    0
);

INSERT INTO answers (name, label, question_id, value, enabled, created_date, created_by, lock_version) VALUES (
    'Sexo-Masculino',
    'Masculino',
    (SELECT id FROM questions WHERE name = 'SEX'),
    2,
    true,
    now(),
    'admin',
    0
);

INSERT INTO questions (name, label, type, is_open, enabled, created_date, created_by, lock_version) VALUES (
    'BIRTH_DATE',
    'Fecha de nacimiento',
    'CALENDAR',
    true,
    true,
    now(),
    'admin',
    0
);

INSERT INTO questions (name, label, type, is_open, enabled, created_date, created_by, lock_version) VALUES (
    'AGE',
    'Edad',
    'NUMBER',
    true,
    true,
    now(),
    'admin',
    0
);

INSERT INTO service_terms (name, description, text, enabled, created_date, created_by, lock_version) VALUES (
    'default',
    'Términos y condiciones generales',
    'Terminos y condiciones xxxxxxxx',
    true,
    now(),
    'admin',
    0
);

INSERT INTO brands (name, description, cobrand, logo_image_url, background_image_url, enabled, created_date, created_by, lock_version) VALUES (
    'municipalidad-cordoba',
    'Personalización para los routers de las paradas de la Municipalidad de Córdoba',
    'Municipalidad de Córdoba',
    './cdn/login/logo.png',
    './cdn/login/brand.jpg',
    true,
    now(),
    'admin',
    0
);

INSERT INTO segments (lock_version,created_by,created_date,enabled,name) VALUES (
	0,
	'admin',
	now(),
	TRUE,
	'segmento-uno'
);

INSERT INTO segments (lock_version,created_by,created_date,enabled,name) VALUES (
	0,
	'admin',
	now(),
	TRUE,
	'segmento-dos'
);

INSERT INTO segment_items (lock_version,created_by,created_date,enabled,OPERATOR,VALUE,QUESTION_ID,SEGMENT_ID) VALUES (
	0,
	'admin',
	now(),
	TRUE,
	'GREATER_THAN',
	'18',
	3,
	1
);

INSERT INTO segment_items (lock_version,created_by,created_date,enabled,OPERATOR,VALUE,QUESTION_ID,SEGMENT_ID) VALUES (
	0,
	'admin',
	now(),
	TRUE,
	'EQUAL',
	'2',
	1,
	1
);

INSERT INTO segment_items (lock_version,created_by,created_date,enabled,OPERATOR,VALUE,QUESTION_ID,SEGMENT_ID) VALUES (
	0,
	'admin',
	now(),
	TRUE,
	'GREATER_THAN',
	'20',
	2,
	2
);

INSERT INTO campaigns (name, description, landing_url, enabled, created_by, created_date, lock_version) VALUES (
    'omnibus-municipalidad-de-cordoba',
    'Campaña publicitaria para las paradas de omnibus de la Municipalidad de Córdoba',
    'www.vates.com',
    TRUE,
    'admin',
	now(),
	0	
);

INSERT INTO advertisements (lock_version, created_by, created_date, enabled, description, NAME,TYPE,DURATION,PRIORITY,START_DATE,SOURCE_URL,CAMPAIGN_ID,SEGMENT_ID) VALUES (
	0,
	'admin',
	now(),
	TRUE,
	'Video publicitario de la Municipalidad de Córdoba',
	'video-municipalidad-cordoba',
	'VIDEO',
	15,
	1,
	now(),
	'//docs.google.com/uc?id=0B-ChTGqpPpqgb3pWSkFTZFlYV3M',
	1,
	1
);

INSERT INTO advertisements (name, description, type, duration, priority, start_date, source_url, text, campaign_id, segment_id, created_by, created_date, enabled, lock_version) VALUES (
	'banner-coca-cola',
	'Publicidad de Coca Cola',
	'BANNER',
	10,
    2,
    now(),
    './cdn/campaign/ads/banners/201.jpg',
    'Coca Cola refresca tu desarrollo',
    1,
    1,
	'admin',
	now(),
	TRUE,
    0
);

INSERT INTO router_groups (name, description, brand_id, enabled, created_date, created_by, lock_version) VALUES (
    'omnibus-municipalidad-cordoba',
    'Routers en las paradas de bus, Municipalidad de Córdoba',
    (SELECT id FROM brands WHERE name = 'municipalidad-cordoba'),
    true,
    now(),
    'admin',
    0
);

INSERT INTO router_groups_buttons (group_id, button) VALUES (
	(SELECT id FROM router_groups WHERE name = 'omnibus-municipalidad-cordoba'),
	1
);

INSERT INTO router_groups_buttons (group_id, button) VALUES (
	(SELECT id FROM router_groups WHERE name = 'omnibus-municipalidad-cordoba'),
	2
);

INSERT INTO router_groups_buttons (group_id, button) VALUES (
    (SELECT id FROM router_groups WHERE name = 'omnibus-municipalidad-cordoba'),
    3
);

INSERT INTO router_groups_questions (group_id, question_id) VALUES (
	(SELECT id FROM router_groups WHERE name = 'omnibus-municipalidad-cordoba'),
	(SELECT id FROM questions WHERE name = 'SEX')
);

INSERT INTO router_groups_questions (group_id, question_id) VALUES (
	(SELECT id FROM router_groups WHERE name = 'omnibus-municipalidad-cordoba'),
	(SELECT id FROM questions WHERE name = 'BIRTH_DATE')
);

INSERT INTO routers (name, description, mac_address, ip_v4_address, group_id, location, latitude, longitude, enabled, created_date, created_by, lock_version) VALUES (
    'omnibus-municipalidad-cordoba-1',
    'Router de prueba',
    'E4:8D:8C:4D:9E:34',
    '192.168.110.124',
    (SELECT id FROM router_groups WHERE name = 'omnibus-municipalidad-cordoba'),
    'Avenida Colón 900, Córdoba, Cordoba, Argentina',
    -31.409886559697128,
    -64.19542049999995,
    true,
    now(),
    'admin',
    0
);

INSERT INTO hotspots (name, description, router_id, enabled, created_date, created_by, lock_version) VALUES (
    'hotspot-wifibus',
    'Hostpost de prueba',
    (SELECT id FROM routers WHERE name = 'omnibus-municipalidad-cordoba-1'),
    true,
    now(),
    'admin',
    0
);







