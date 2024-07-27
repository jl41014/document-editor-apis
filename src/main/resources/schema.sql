create table document_content (
  id                int NOT NULL AUTO_INCREMENT,
  block             clob NOT NULL,
  type              varchar(20) NOT NULL,
  create_time       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

create table document (
  doc_name          varchar(50) NOT NULL,
  type              varchar(20) NOT NULL,
  block_list        varchar(2000) NOT NULL,
  header            int,
  footer            int,
  create_time       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (doc_name, type)
);