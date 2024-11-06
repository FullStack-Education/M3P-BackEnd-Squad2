-- Inserir perfis
INSERT INTO perfis (nome)
SELECT 'administrador' WHERE NOT EXISTS (SELECT 1 FROM perfis WHERE nome = 'administrador');

INSERT INTO perfis (nome)
SELECT 'docente' WHERE NOT EXISTS (SELECT 1 FROM perfis WHERE nome = 'docente');

INSERT INTO perfis (nome)
SELECT 'aluno' WHERE NOT EXISTS (SELECT 1 FROM perfis WHERE nome = 'aluno');

-- Inserir cursos
INSERT INTO cursos (nome, duracao)
SELECT 'Fullstack', '200 horas' WHERE NOT EXISTS (SELECT 1 FROM cursos WHERE nome = 'Fullstack');

INSERT INTO cursos (nome, duracao)
SELECT 'FuturoDEV', '100 horas' WHERE NOT EXISTS (SELECT 1 FROM cursos WHERE nome = 'FuturoDEV');

INSERT INTO cursos (nome, duracao)
SELECT 'DEVStart', '50 horas' WHERE NOT EXISTS (SELECT 1 FROM cursos WHERE nome = 'DEVStart');

-- Inserir materias
INSERT INTO materias (nome, curso_id)
SELECT 'Java', 1 WHERE NOT EXISTS (SELECT 1 FROM materias WHERE nome = 'Java');

INSERT INTO materias (nome, curso_id)
SELECT 'Spring', 1 WHERE NOT EXISTS (SELECT 1 FROM materias WHERE nome = 'Spring');

INSERT INTO materias (nome, curso_id)
SELECT 'Angular', 1 WHERE NOT EXISTS (SELECT 1 FROM materias WHERE nome = 'Angular');

INSERT INTO materias (nome, curso_id)
SELECT 'Python', 2 WHERE NOT EXISTS (SELECT 1 FROM materias WHERE nome = 'Python');

INSERT INTO materias (nome, curso_id)
SELECT 'Flask', 2 WHERE NOT EXISTS (SELECT 1 FROM materias WHERE nome = 'Flask');

INSERT INTO materias (nome, curso_id)
SELECT 'JavaScript', 2 WHERE NOT EXISTS (SELECT 1 FROM materias WHERE nome = 'JavaScript');

INSERT INTO materias (nome, curso_id)
SELECT 'PHP', 3 WHERE NOT EXISTS (SELECT 1 FROM materias WHERE nome = 'PHP');

INSERT INTO materias (nome, curso_id)
SELECT 'Laravel', 3 WHERE NOT EXISTS (SELECT 1 FROM materias WHERE nome = 'Laravel');

INSERT INTO materias (nome, curso_id)
SELECT 'Node.js', 3 WHERE NOT EXISTS (SELECT 1 FROM materias WHERE nome = 'Node.js');

-- Inserir usuários
INSERT INTO usuarios (email, senha, perfil_id)
SELECT 'administrador@gmail.com', '$2a$09$p7ay7ydkqUwN/xe73uqn3.OwvqVoAkuGZeNZ6XgwMBfHAvzmQphBq', 1
    WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE perfil_id = 1);


-- Inserir docente admin
INSERT INTO docentes (nome, usuario_id, telefone, genero, estado_civil, data_nascimento, cpf, rg, naturalidade, cep, uf)
SELECT 'Administrador do Sistema', id, '123456789000', 'masculino', 'casado', '1999-01-08', '12345678900', 'MG-12.345.678', 'cidade admin', '12345-678', 'MG'
FROM usuarios
WHERE email = 'administrador@gmail.com'
  AND NOT EXISTS (SELECT 1 FROM docentes WHERE usuario_id = 1);

-- Inserir docente Carla Pereira
INSERT INTO usuarios (id, email, senha, perfil_id)
SELECT 2000, 'docente@teste.com', '$2a$09$p7ay7ydkqUwN/xe73uqn3.OwvqVoAkuGZeNZ6XgwMBfHAvzmQphBq', 2
    WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE id = 2000);

-- Inserir docente Paulo Souza
INSERT INTO usuarios (id, email, senha, perfil_id)
SELECT 3000, 'paulo@teste.com', '$2a$09$p7ay7ydkqUwN/xe73uqn3.OwvqVoAkuGZeNZ6XgwMBfHAvzmQphBq', 2
    WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE id = 3000);

-- Inserir docente João Santos
INSERT INTO usuarios (id, email, senha, perfil_id)
SELECT 4000, 'joao@teste.com', '$2a$09$p7ay7ydkqUwN/xe73uqn3.OwvqVoAkuGZeNZ6XgwMBfHAvzmQphBq', 2
    WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE id = 4000);

-- Inserir docentes
INSERT INTO docentes (id, nome, usuario_id, telefone, genero, estado_civil, data_nascimento, cpf, rg, naturalidade, cep, logradouro, numero, bairro, uf)
SELECT 2000, 'Carla Pereira', 2000, '123456789888', 'Feminino', 'Solteira', '1990-05-10', '12345678901', 'SC-12.345.678', 'Florianópolis', '88062-000', 'Avenida Afonso Delambert Neto', '13', 'Lagoa da Conceição', 'SC'
    WHERE NOT EXISTS (SELECT 1 FROM docentes WHERE usuario_id = 2000);

INSERT INTO docentes (id, nome, usuario_id, telefone, genero, estado_civil, data_nascimento, cpf, rg, naturalidade, cep, logradouro, numero, bairro, uf)
SELECT 3000, 'Paulo Souza', 3000, '123456789111', 'Masculino', 'Solteiro', '1992-08-15', '23456789012', 'MG-12.345.678', 'Uberlândia', '88062-000', 'Avenida Afonso Delambert Neto', '200', 'Lagoa da Conceição', 'SC'
    WHERE NOT EXISTS (SELECT 1 FROM docentes WHERE usuario_id = 3000);

INSERT INTO docentes (id, nome, usuario_id, telefone, genero, estado_civil, data_nascimento, cpf, rg, naturalidade, cep, logradouro, numero, bairro, uf)
SELECT 4000, 'João Santos', 4000, '989876345678', 'Masculino', 'Casado', '1985-12-25', '34567890123', 'SC-12.345.678', 'Florianópolis', '88062-000','Avenida Afonso Delambert Neto', '91', 'Lagoa da Conceição', 'SC'
    WHERE NOT EXISTS (SELECT 1 FROM docentes WHERE usuario_id = 4000);

-- Associar matérias aos docentes
INSERT INTO docentes_materia (materia_id, docente_id)
SELECT 1, 2000 WHERE NOT EXISTS (SELECT 1 FROM docentes_materia WHERE materia_id = 1 AND docente_id = 2000);
INSERT INTO docentes_materia (materia_id, docente_id)
SELECT 2, 2000 WHERE NOT EXISTS (SELECT 1 FROM docentes_materia WHERE materia_id = 2 AND docente_id = 2000);

INSERT INTO docentes_materia (materia_id, docente_id)
SELECT 4, 3000 WHERE NOT EXISTS (SELECT 1 FROM docentes_materia WHERE materia_id = 4 AND docente_id = 3000);

INSERT INTO docentes_materia (materia_id, docente_id)
SELECT 7, 4000 WHERE NOT EXISTS (SELECT 1 FROM docentes_materia WHERE materia_id = 7 AND docente_id = 4000);

-- Inserir turmas
INSERT INTO turmas (id, nome, data_inicio, data_termino, horario, docente_id, curso_id)
SELECT 1000, 'Turma 01', '2023-01-01', '2023-12-31', '08:00', 2000, 1
    WHERE NOT EXISTS (SELECT 1 FROM turmas WHERE id = 1000)
  AND EXISTS (SELECT 1 FROM docentes WHERE id = 2000)
  AND EXISTS (SELECT 1 FROM cursos WHERE id = 1);

INSERT INTO turmas (id, nome, data_inicio, data_termino, horario, docente_id, curso_id)
SELECT 2000, 'Turma 02', '2023-01-01', '2023-12-31', '10:00', 4000, 3
    WHERE NOT EXISTS (SELECT 1 FROM turmas WHERE id = 2000)
  AND EXISTS (SELECT 1 FROM docentes WHERE id = 4000)
  AND EXISTS (SELECT 1 FROM cursos WHERE id = 3);

INSERT INTO turmas (id, nome, data_inicio, data_termino, horario, docente_id, curso_id)
SELECT 3000, 'Turma 03', '2023-01-01', '2023-12-31', '14:00', 3000, 2
    WHERE NOT EXISTS (SELECT 1 FROM turmas WHERE id = 3000)
  AND EXISTS (SELECT 1 FROM docentes WHERE id = 3000)
  AND EXISTS (SELECT 1 FROM cursos WHERE id = 2);

-- Inserir alunos
INSERT INTO usuarios (id, email, senha, perfil_id)
SELECT 5000, 'maria@teste.com', '$2a$09$p7ay7ydkqUwN/xe73uqn3.OwvqVoAkuGZeNZ6XgwMBfHAvzmQphBq', 3
    WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE id = 5000);

INSERT INTO alunos (id, nome, usuario_id, telefone, genero, data_nascimento, cpf, rg, naturalidade, cep, logradouro, numero, bairro, uf, turma_id)
SELECT 5000, 'Maria Sousa', 5000, '765432347685', 'Feminino', '2000-02-10', '45678901234', 'SC-12.345.678', 'Florianópolis', '88062-000','Avenida Afonso Delambert Neto', '764', 'Lagoa da Conceição', 'SC', 1000
    WHERE NOT EXISTS (SELECT 1 FROM alunos WHERE id = 5000);

INSERT INTO usuarios (id, email, senha, perfil_id)
SELECT 6000, 'carolina@teste.com', '$2a$09$p7ay7ydkqUwN/xe73uqn3.OwvqVoAkuGZeNZ6XgwMBfHAvzmQphBq', 3
    WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE id = 6000);

INSERT INTO alunos (id, nome, usuario_id, telefone, genero, data_nascimento, cpf, rg, naturalidade, cep, logradouro, numero, bairro, uf, turma_id)
SELECT 6000, 'Carolina Maria', 6000, '888734523894', 'Feminino', '1999-04-20', '56789012345', 'MG-12.345.678', 'Belo Horizonte', '88062-000','Avenida Afonso Delambert Neto', '721', 'Lagoa da Conceição', 'SC', 2000
    WHERE NOT EXISTS (SELECT 1 FROM alunos WHERE id = 6000);

INSERT INTO usuarios (id, email, senha, perfil_id)
SELECT 7000, 'jose@teste.com', '$2a$09$p7ay7ydkqUwN/xe73uqn3.OwvqVoAkuGZeNZ6XgwMBfHAvzmQphBq', 3
    WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE id = 7000);

INSERT INTO alunos (id, nome, usuario_id, telefone, genero, data_nascimento, cpf, rg, naturalidade, cep, logradouro, numero, bairro, uf, turma_id)
SELECT 7000, 'José Silva', 7000, '998323456789', 'Masculino', '1998-11-30', '67890123456', 'SC-12.345.678', 'Florianópolis', '88062-000', 'Avenida Afonso Delambert Neto', '87', 'Lagoa da Conceição', 'SC', 1000
    WHERE NOT EXISTS (SELECT 1 FROM alunos WHERE id = 7000);

-- Inserir avaliações
INSERT INTO avaliacoes (id, nome, valor, data_avaliacao, aluno_id, professor_id, materia_id)
SELECT 1000, 'Avaliação 1', 7.8, '2024-10-01', 5000, 2000, (SELECT id FROM materias WHERE nome = 'Java')
    WHERE NOT EXISTS (SELECT 1 FROM avaliacoes WHERE id = 1000);

INSERT INTO avaliacoes (id, nome, valor, data_avaliacao, aluno_id, professor_id, materia_id)
SELECT 2000, 'Avaliação 2', 9.5, '2024-10-05', 5000, 2000, (SELECT id FROM materias WHERE nome = 'Spring')
    WHERE NOT EXISTS (SELECT 1 FROM avaliacoes WHERE id = 2000);

INSERT INTO avaliacoes (id, nome, valor, data_avaliacao, aluno_id, professor_id, materia_id)
SELECT 3000, 'Avaliação 3', 8.2, '2024-10-10', 5000, 2000, (SELECT id FROM materias WHERE nome = 'Java')
    WHERE NOT EXISTS (SELECT 1 FROM avaliacoes WHERE id = 3000);
