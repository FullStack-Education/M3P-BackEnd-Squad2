INSERT INTO perfis (nome)
SELECT 'administrador'
    WHERE NOT EXISTS (SELECT 1 FROM perfis WHERE nome = 'administrador');

INSERT INTO perfis (nome)
SELECT 'docente'
    WHERE NOT EXISTS (SELECT 1 FROM perfis WHERE nome = 'docente');

INSERT INTO perfis (nome)
SELECT 'aluno'
    WHERE NOT EXISTS (SELECT 1 FROM perfis WHERE nome = 'aluno');


INSERT INTO cursos (nome, duracao)
SELECT 'Fullstack', '200 horas'
    WHERE NOT EXISTS (SELECT 1 FROM cursos WHERE nome = 'Fullstack');

INSERT INTO cursos (nome, duracao)
SELECT 'FuturoDEV', '100 horas'
    WHERE NOT EXISTS (SELECT 1 FROM cursos WHERE nome = 'FuturoDEV');

INSERT INTO cursos (nome, duracao)
SELECT 'DEVStart', '50 horas'
    WHERE NOT EXISTS (SELECT 1 FROM cursos WHERE nome = 'DEVStart');


INSERT INTO materias (nome, curso_id)
SELECT 'Java', 1
    WHERE NOT EXISTS (SELECT 1 FROM materias WHERE nome = 'Java');

INSERT INTO materias (nome, curso_id)
SELECT 'Spring', 1
    WHERE NOT EXISTS (SELECT 1 FROM materias WHERE nome = 'Spring');

INSERT INTO materias (nome, curso_id)
SELECT 'Angular', 1
    WHERE NOT EXISTS (SELECT 1 FROM materias WHERE nome = 'Angular');

INSERT INTO materias (nome, curso_id)
SELECT 'Python', 2
    WHERE NOT EXISTS (SELECT 1 FROM materias WHERE nome = 'Python');

INSERT INTO materias (nome, curso_id)
SELECT 'Flask', 2
    WHERE NOT EXISTS (SELECT 1 FROM materias WHERE nome = 'Flask');

INSERT INTO materias (nome, curso_id)
SELECT 'JavaScript', 2
    WHERE NOT EXISTS (SELECT 1 FROM materias WHERE nome = 'JavaScript');

INSERT INTO materias (nome, curso_id)
SELECT 'PHP', 3
    WHERE NOT EXISTS (SELECT 1 FROM materias WHERE nome = 'PHP');

INSERT INTO materias (nome, curso_id)
SELECT 'Laravel', 3
    WHERE NOT EXISTS (SELECT 1 FROM materias WHERE nome = 'Laravel');

INSERT INTO materias (nome, curso_id)
SELECT 'Node.js', 3
    WHERE NOT EXISTS (SELECT 1 FROM materias WHERE nome = 'Node.js');


INSERT INTO usuarios (email, senha, perfil_id)
SELECT 'administrador@gmail.com', '$2a$09$p7ay7ydkqUwN/xe73uqn3.OwvqVoAkuGZeNZ6XgwMBfHAvzmQphBq', 1
    WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE perfil_id = 1);


INSERT INTO docentes (nome, usuario_id, telefone, genero, estado_civil, data_nascimento, cpf, rg, naturalidade, cep, uf)
SELECT 'admin', id, '123456789', 'masculino', 'casado', '1999-01-08', '123.456.789-00', 'MG-12.345.678', 'cidade admin', '12345-678', 'MG'
FROM usuarios
WHERE email = 'administrador@gmail.com'
  AND NOT EXISTS (SELECT 1 FROM docentes WHERE usuario_id = 1);
