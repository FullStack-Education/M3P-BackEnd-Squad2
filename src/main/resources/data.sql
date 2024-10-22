INSERT INTO perfis (nome)
VALUES
('administrador'),
('docente'),
('aluno')
ON CONFLICT (nome) DO NOTHING;

INSERT INTO cursos (nome, duracao)
VALUES
('Fullstack', '200 horas'),
('FuturoDEV', '100 horas'),
('DEVStart', '50 horas')
ON CONFLICT (nome) DO NOTHING;

INSERT INTO materias (nome, curso_id)
VALUES
('Java', 1),
('Spring', 1),
('Angular', 1),
('Python', 2),
('Flask', 2),
('JavaScript', 2),
('PHP', 3),
('Laravel', 3),
('Node.js', 3)
ON CONFLICT (nome) DO NOTHING;

INSERT INTO usuarios (email, senha, perfil_id)
SELECT 'administrador@gmail.com', '$2a$09$p7ay7ydkqUwN/xe73uqn3.OwvqVoAkuGZeNZ6XgwMBfHAvzmQphBq', 1
WHERE NOT EXISTS (
    SELECT 1 FROM usuarios WHERE perfil_id = 1
);

INSERT INTO docentes (nome, usuario_id, telefone, genero, estado_civil, data_nascimento, cpf, rg, naturalidade, cep, uf)
SELECT 'admin', id, '123456789', 'masculino', 'casado', '1999-01-08', '123.456.789-00', 'MG-12.345.678', 'cidade admin', '12345-678', 'MG'
FROM usuarios
WHERE email = 'administrador@gmail.com'
AND NOT EXISTS (
    SELECT 1 FROM docentes WHERE usuario_id = 1
);

