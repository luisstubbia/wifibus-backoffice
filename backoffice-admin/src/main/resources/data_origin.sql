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

INSERT INTO users (username, password_hash, first_name, last_name, role, enabled, created_date, created_by, lock_version) VALUES (
  'user',
  '$2a$10$h0Eo.3bupGsgGEvZUqe4XeMr6yToDojkqqw28YfFA6eT/dQI6w36K', -- demo
  'User',
  'Test',
  'USER',
  TRUE,
  now(),
  'admin',
  0
);

INSERT INTO users (username, password_hash, first_name, last_name, role, enabled, created_date, created_by, lock_version) VALUES (
  'jperez',
  '$2a$10$h0Eo.3bupGsgGEvZUqe4XeMr6yToDojkqqw28YfFA6eT/dQI6w36K', -- demo
  'Juan',
  'Perez',
  'USER',
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
    'Terminos y condiciones generales',
    'Terminos y condiciones xxxxxxxx',
    true,
    now(),
    'admin',
    0
);

INSERT INTO service_terms (lock_version, created_by, created_date, enabled, description, name, text) VALUES (
	0,
	'admin',
	now(),
	TRUE,
	'Terminos y Condiciones de Córdoba',
	'Municipalidad de Córdoba',
	'Terminos y condiciones para la Municipalidad de Córdoba'
);

INSERT INTO service_terms (lock_version, created_by, created_date, enabled, name, text) VALUES (
	0,
	'admin',
	now(),
	TRUE,
	'terms2',
	'textTerms2'
);

INSERT INTO brands (name, description, cobrand, logo_image_url, background_image_url, enabled, created_date, created_by, lock_version) VALUES (
    'municipalidad-cordoba',
    'Personalizacion para los routers de las paradas de la Municipalidad de Cordoba',
    'Municipalidad de C&oacute;rdoba',
    '//docs.google.com/uc?id=0B-ChTGqpPpqgME5GQlVfbXpFRjA',
    '//docs.google.com/uc?id=0B-ChTGqpPpqgb3pWSkFTZFlYV3M',
    true,
    now(),
    'admin',
    0
);

INSERT INTO brands (lock_version, created_by, created_date, enabled, name, background_image_url, cobrand, logo_image_url) VALUES (
	0,
	'admin',
	now(),
	TRUE,
	'Municipalidad de Córdoba',
	'//docs.google.com/uc?id=0B-ChTGqpPpqgb3pWSkFTZFlYV3M',
	'Municipalidad de Córdoba',
	'//docs.google.com/uc?id=0B-ChTGqpPpqgME5GQlVfbXpFRjA'
);

INSERT INTO brands (lock_version, created_by, created_date, enabled, name, background_image_url, cobrand, logo_image_url) VALUES (
	0,
	'admin',
	now(),
	TRUE,
	'branding2',
	'imgFondo2',
	'cobrand2',
	'imgLogo2'
);

INSERT INTO brands (name, description, cobrand, logo_image_url, background_image_url, enabled, created_date, created_by, lock_version) VALUES (
    'mc-donalds',
    'Personalizacion para los routers de McDonald''''s',
    'McDonald''''s',
    '//d701vexhkz032.cloudfront.net/bundles/front/media/images/header/mcdonalds-logo.png',
    '//d701vexhkz032.cloudfront.net/bundles/front/media/images/banner/mcdls3.jpg',
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

INSERT INTO campaigns (lock_version,created_by,created_date,enabled,description,NAME,landing_url) VALUES (
	0,
	'admin',
	now(),
	TRUE,
	'Campaña publicitaria para la Municipalidad de Córdoba',
	'municipalidad-de-cordoba',
	'627442937445-21nlqm6c0o085el5jqagha86614r5uf3.wifibus.com'
);

INSERT INTO campaigns (lock_version,created_by,created_date,enabled,NAME,landing_url) VALUES (
	0,
	'admin',
	now(),
	TRUE,
	'Campaña-dos',
	'paginaDestinoDos'
);

INSERT INTO advertisements (lock_version,created_by,created_date,enabled,description,NAME,TYPE,DURATION,PRIORITY,START_DATE,SOURCE_URL,CAMPAIGN_ID,SEGMENT_ID) VALUES (
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

INSERT INTO advertisements (lock_version,created_by,created_date,enabled,description,NAME,TYPE,DURATION,PRIORITY,START_DATE,SOURCE_URL,TEXT,CAMPAIGN_ID,SEGMENT_ID) VALUES (
	0,
	'admin',
	now(),
	TRUE,
	'Banner publicitario de la Municipalidad de Córdoba',
	'banner-municipalidad-cordoba',
	'BANNER',
	10,
	2,
	now(),
	'//docs.google.com/uc?id=0B-ChTGqpPpqgME5GQlVfbXpFRjA',
	'Municipalidad de Córdoba',
	1,
	1
);

INSERT INTO router_groups (name, description, enabled, created_date, created_by, lock_version) VALUES (
    'mc-donalds',
    'Routers en los restaurantes Mc Donalds',
    true,
    now(),
    'admin',
    0
);

INSERT INTO router_groups (name, description, enabled, created_date, created_by, lock_version) VALUES (
    'il-gatto',
    'Routers en los restaurantes Il Gatto',
    true,
    now(),
    'admin',
    0
);

INSERT INTO router_groups (name, description, brand_id, enabled, created_date, created_by, lock_version) VALUES (
    'bus-sur',
    'Routers en las paradas de bus, zona sur',
    (SELECT id FROM brands WHERE name = 'municipalidad-cordoba'),
    true,
    now(),
    'admin',
    0
);

INSERT INTO router_groups (name, description, brand_id, enabled, created_date, created_by, lock_version) VALUES (
    'bus-norte',
    'Routers en las paradas de bus, zona norte',
    (SELECT id FROM brands WHERE name = 'municipalidad-cordoba'),
    true,
    now(),
    'admin',
    0
);

INSERT INTO router_groups (name, description, brand_id, enabled, created_date, created_by, lock_version) VALUES (
    'bus-este',
    'Routers en las paradas de bus, zona este',
    (SELECT id FROM brands WHERE name = 'municipalidad-cordoba'),
    true,
    now(),
    'admin',
    0
);

INSERT INTO router_groups (name, description, brand_id, enabled, created_date, created_by, lock_version) VALUES (
    'bus-oeste',
    'Routers en las paradas de bus, zona oeste',
    (SELECT id FROM brands WHERE name = 'municipalidad-cordoba'),
    true,
    now(),
    'admin',
    0
);

INSERT INTO router_groups (name, description, brand_id, enabled, created_date, created_by, lock_version) VALUES (
    'bus-centro',
    'Routers en las paradas de bus, zona centro',
    (SELECT id FROM brands WHERE name = 'municipalidad-cordoba'),
    true,
    now(),
    'admin',
    0
);

INSERT INTO router_groups (name, description, brand_id, enabled, created_date, created_by, lock_version) VALUES (
    'bus-circunvalacion',
    'Routers en las paradas de bus, zona circunvalacion',
    (SELECT id FROM brands WHERE name = 'municipalidad-cordoba'),
    true,
    now(),
    'admin',
    0
);

INSERT INTO router_groups (name, description, enabled, created_date, created_by, lock_version) VALUES (
    'aeropuerto-cba',
    'Routers de aeropuerto de Cordoba',
    true,
    now(),
    'admin',
    0
);

INSERT INTO router_groups (name, description, enabled, created_date, created_by, lock_version) VALUES (
    'aeroparque-bue',
    'Routers de aeroparque de Buenos Aires',
    true,
    now(),
    'admin',
    0
);

INSERT INTO router_groups (name, description, enabled, created_date, created_by, lock_version) VALUES (
    'aeropuerto-mza',
    'Routers de aeropuerto de Mendoza',
    true,
    now(),
    'admin',
    0
);

INSERT INTO router_groups_buttons (group_id, button) VALUES (
	(SELECT id FROM router_groups WHERE name = 'aeroparque-bue'),
	1
);

INSERT INTO router_groups_buttons (group_id, button) VALUES (
	(SELECT id FROM router_groups WHERE name = 'aeroparque-bue'),
	2
);
INSERT INTO router_groups_questions (group_id, question_id) VALUES (
	(SELECT id FROM router_groups WHERE name = 'aeroparque-bue'),
	(SELECT id FROM questions WHERE name = 'SEX')
);

INSERT INTO router_groups_questions (group_id, question_id) VALUES (
	(SELECT id FROM router_groups WHERE name = 'aeroparque-bue'),
	(SELECT id FROM questions WHERE name = 'AGE')
);

INSERT INTO routers (name, description, mac_address, ip_v4_address, group_id, location, latitude, longitude, enabled, created_date, created_by, lock_version) VALUES (
    'mc-donalds-1',
    'Router en el Mc Donalds de Av. Colon al 900',
    'xx:xx:xx:xx:xx:11',
    '192.168.0.21',
    (SELECT id FROM router_groups WHERE name = 'mc-donalds'),
    'Avenida Colón 900, Córdoba, Cordoba, Argentina',
    -31.409886559697128,
    -64.19542049999995,
    true,
    now(),
    'admin',
    0
);

INSERT INTO routers (name, description, mac_address, ip_v4_address, group_id, location, latitude, longitude, enabled, created_date, created_by, lock_version) VALUES (
    'mc-donalds-2',
    'Router en el Mc Donalds de Alto Palermo, Av. Santa Fe 3253',
    'xx:xx:xx:xx:xx:22',
    '192.168.0.22',
    (SELECT id FROM router_groups WHERE name = 'mc-donalds'),
    'Avenida Colón 900, Córdoba, Cordoba, Argentina',
    -34.58841311095033,
    -58.41079669999999,
    true,
    now(),
    'admin',
    0
);

INSERT INTO routers (name, description, mac_address, ip_v4_address, group_id, location, latitude, longitude, enabled, created_date, created_by, lock_version) VALUES (
    'mc-donalds-3',
    'Router en el Mc Donalds de Av. Santa Fe 3684',
    'xx:xx:xx:xx:xx:33',
    '192.168.0.23',
    (SELECT id FROM router_groups WHERE name = 'mc-donalds'),
    'Avenida Santa Fe 3684, Buenos Aires, Argentina',
    -34.58531316094909,
    -58.415916100000004,
    true,
    now(),
    'admin',
    0
);

INSERT INTO routers (name, description, mac_address, ip_v4_address, group_id, location, latitude, longitude, enabled, created_date, created_by, lock_version) VALUES (
    'il-gatto-1',
    'Router en el Il Gatto de Billinghurst 1833',
    'yy:yy:yy:yy:yy:yy',
    '192.168.160.21',
    (SELECT id FROM router_groups WHERE name = 'il-gatto'),
    'Billinghurst 1833, CABA, Buenos Aires, Argentina',
    -34.58929471095069,
    -58.40924055000005,
    true,
    now(),
    'admin',
    0
);

INSERT INTO hotspots (name, description, router_id, enabled, created_date, created_by, lock_version) VALUES (
    'mc-donalds-1',
    'Hostpost para el router mc-donalds-1',
    (SELECT id FROM routers WHERE name = 'mc-donalds-1'),
    true,
    now(),
    'admin',
    0
);







