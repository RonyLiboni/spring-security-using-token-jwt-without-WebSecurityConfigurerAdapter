CREATE TABLE users (
  user_id varbinary(36) NOT NULL,
  password varchar(150) NOT NULL,
  username varchar(150) NOT NULL UNIQUE KEY,
  password_recovery_token varchar(60) DEFAULT NULL,
  PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE roles (
  role_id bigint NOT NULL AUTO_INCREMENT,
  role_name varchar(255) NOT NULL UNIQUE KEY,
  PRIMARY KEY (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO roles (role_id, role_name) 
VALUES (1, 'ROLE_ADMIN');

INSERT INTO roles (role_id, role_name) 
VALUES (2, 'ROLE_USER');

CREATE TABLE users_roles (
  user_model_user_id varbinary(36) NOT NULL,
  roles_role_id bigint NOT NULL,
  FOREIGN KEY (user_model_user_id) REFERENCES users (user_id),
  FOREIGN KEY (roles_role_id) REFERENCES roles (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO users (user_id, password, username, password_recovery_token) 
VALUES (UUID_TO_BIN('15b53dce-32c5-475d-aa1c-7e0fa1682b24'), '$2a$10$EruneaH.Sm81RZs2U6Zwpen2o1oaHOD5oCnzfsMj59IyeysEm4yYu', 'ronald.liboni@acad.pucrs.br', 'testToken');

INSERT INTO users_roles (user_model_user_id, roles_role_id)
VALUES (UUID_TO_BIN('15b53dce-32c5-475d-aa1c-7e0fa1682b24'), 1);
