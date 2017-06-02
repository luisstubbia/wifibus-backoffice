
INSERT INTO questions (
    name,
    label,
    type,
    is_open,
    enabled,
    created_date,
    created_by,
    lock_version
)
VALUES
(
    'SEX',
    'Sexo',
    'DROPDOWN',
    false,
    true,
    now(),
    'admin',
    0
);

INSERT INTO answers (
    name,
    label,
    question_id,
    value,
    enabled,
    created_date,
    created_by,
    lock_version
)
VALUES
(
    'Sexo-Femenino',
    'Femenino',
    (SELECT id FROM questions WHERE name = 'SEX'),
    1,
    true,
    now(),
    'admin',
    0
);
INSERT INTO answers (
    name,
    label,
    question_id,
    value,
    enabled,
    created_date,
    created_by,
    lock_version
)
VALUES
(
    'Sexo-Masculino',
    'Masculino',
    (SELECT id FROM questions WHERE name = 'SEX'),
    2,
    true,
    now(),
    'admin',
    0
);

INSERT INTO questions (
    name,
    label,
    type,
    is_open,
    enabled,
    created_date,
    created_by,
    lock_version
)
VALUES
(
    'BIRTH_DATE',
    'Fecha de nacimiento',
    'CALENDAR',
    true,
    true,
    now(),
    'admin',
    0
);

INSERT INTO questions (
    name,
    label,
    type,
    is_open,
    enabled,
    created_date,
    created_by,
    lock_version
)
VALUES
(
    'AGE',
    'Edad',
    'NUMBER',
    true,
    true,
    now(),
    'admin',
    0
);

