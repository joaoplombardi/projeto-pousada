DROP TABLE t_pousada_quartos CASCADE CONSTRAINTS;

DROP TABLE t_pousada_reserva CASCADE CONSTRAINTS;


CREATE TABLE t_pousada_quartos
(
    id_quarto       NUMBER(2)    NOT NULL PRIMARY KEY,
    ds_categoria    VARCHAR2(15) NOT NULL,
    qt_max_pessoas  NUMBER(2)    NOT NULL,
    ds_valor_diaria NUMBER(5, 2) NOT NULL
);

CREATE SEQUENCE sq_reserva increment by 1 start with 1 nocycle order;

CREATE TABLE t_pousada_reserva
(
    id_reserva NUMBER(5) NOT NULL PRIMARY KEY,
    id_quarto  NUMBER(2) NOT NULL,
    dt_entrada DATE      NOT NULL,
    dt_saida   DATE,
    qt_pessoas NUMBER(2) NOT NULL
);



