-- Gerado por Oracle SQL Developer Data Modeler 20.2.0.167.1538
--   em:        2020-08-29 14:27:54 BRT
--   site:      Oracle Database 11g
--   tipo:      Oracle Database 11g



DROP TABLE t_pousada_quartos CASCADE CONSTRAINTS;

DROP TABLE t_pousada_reserva CASCADE CONSTRAINTS;

-- predefined type, no DDL - MDSYS.SDO_GEOMETRY

-- predefined type, no DDL - XMLTYPE

CREATE TABLE t_pousada_quartos (
    id_quarto        NUMBER(2) NOT NULL,
    ds_categoria     VARCHAR2(15) NOT NULL,
    qt_max_pessoas   NUMBER(2) NOT NULL,
    ds_valor_diaria  NUMBER(5, 2) NOT NULL
);

CREATE SEQUENCE sq_reserva increment by 1 start with 1 nocycle order;

CREATE UNIQUE INDEX t_pousada_quartos__idx ON
    t_pousada_quartos (
        ds_categoria
    ASC );

ALTER TABLE t_pousada_quartos
    ADD CONSTRAINT ck_pousada_quarto_categoria CHECK ( ds_categoria = 'VIP'
                                                       OR ds_categoria = 'APARTAMENTO' );

ALTER TABLE t_pousada_quartos ADD CONSTRAINT t_pousada_quartos_pk PRIMARY KEY ( id_quarto );

CREATE TABLE t_pousada_reserva (
    id_reserva  NUMBER(5) NOT NULL,
    id_quarto   NUMBER(2) NOT NULL,
    dt_entrada  DATE NOT NULL,
    dt_saida    DATE,
    qt_pessoas  NUMBER(2) NOT NULL
);

ALTER TABLE t_pousada_reserva ADD CONSTRAINT t_pousada_reserva_pk PRIMARY KEY ( id_reserva );

ALTER TABLE t_pousada_reserva
    ADD CONSTRAINT t_pousada_reserva_quartos_fk FOREIGN KEY ( id_quarto )
        REFERENCES t_pousada_quartos ( id_quarto );



-- Relat�rio do Resumo do Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                             2
-- CREATE INDEX                             1
-- ALTER TABLE                              4
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE MATERIALIZED VIEW LOG             0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
