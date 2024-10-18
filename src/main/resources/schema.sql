CREATE TABLE IF NOT EXISTS perfis (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS usuarios (
    id SERIAL PRIMARY KEY,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(60) NOT NULL UNIQUE,
    perfil_id BIGINT NOT NULL REFERENCES perfis(id)
);

CREATE TABLE IF NOT EXISTS cursos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL UNIQUE,
    duracao VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS docentes (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    telefone VARCHAR(16) NOT NULL,
    genero VARCHAR(50) NOT NULL,
    estado_civil VARCHAR(50) NOT NULL,
    data_nascimento DATE NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    rg VARCHAR(20) NOT NULL,
    naturalidade VARCHAR(64) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    logradouro VARCHAR(100),
    numero VARCHAR(5),
    complemento VARCHAR(100),
    bairro VARCHAR(100),
    uf VARCHAR(2) NOT NULL,
    referencia VARCHAR(100),
    usuario_id BIGINT NOT NULL UNIQUE REFERENCES usuarios(id)
);

CREATE TABLE IF NOT EXISTS materias (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL UNIQUE,
    curso_id BIGINT NOT NULL REFERENCES cursos(id)
);

CREATE TABLE IF NOT EXISTS docentes_materia (
    id SERIAL PRIMARY KEY,
    materia_id BIGINT NOT NULL REFERENCES materias(id),
    docente_id BIGINT NOT NULL REFERENCES docentes(id)
);

CREATE TABLE IF NOT EXISTS turmas (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    data_inicio DATE NOT NULL,
    data_termino DATE NOT NULL,
    horario VARCHAR(15) NOT NULL,
    docente_id BIGINT NOT NULL REFERENCES docentes(id),
    curso_id BIGINT NOT NULL REFERENCES cursos(id)
);


CREATE TABLE IF NOT EXISTS alunos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    telefone VARCHAR(16) NOT NULL,
    genero VARCHAR(50) NOT NULL,
    data_nascimento DATE NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    rg VARCHAR(20) NOT NULL,
    naturalidade VARCHAR(64) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    logradouro VARCHAR(100) NOT NULL,
    numero VARCHAR(5) NOT NULL,
    complemento VARCHAR(100) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    referencia VARCHAR(100) NOT NULL,
    turma_id BIGINT NOT NULL REFERENCES turmas(id),
    usuario_id BIGINT NOT NULL UNIQUE REFERENCES usuarios(id)
);

CREATE TABLE IF NOT EXISTS avaliacoes (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    valor NUMERIC(5, 2) NOT NULL,
    data_avaliacao DATE NOT NULL,
    aluno_id BIGINT NOT NULL REFERENCES alunos(id),
    professor_id BIGINT NOT NULL REFERENCES docentes(id),
    materia_id BIGINT NOT NULL REFERENCES materias(id)
);