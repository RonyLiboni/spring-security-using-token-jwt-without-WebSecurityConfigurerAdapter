CREATE TABLE users (
  user_id varbinary(36) NOT NULL,
  password varchar(150) NOT NULL,
  username varchar(150) NOT NULL UNIQUE KEY,
  password_recovery_token varchar(60) DEFAULT NULL,
  PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE roles (
  role_name varchar(255) NOT NULL UNIQUE KEY,
  PRIMARY KEY (role_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO roles (role_name) 
VALUES ('ROLE_ADMIN');

INSERT INTO roles (role_name) 
VALUES ('ROLE_USER');

CREATE TABLE users_roles (
  user_model_user_id varbinary(36) NOT NULL,
  roles_role_name varchar(50) NOT NULL,
  FOREIGN KEY (user_model_user_id) REFERENCES users (user_id),
  FOREIGN KEY (roles_role_name) REFERENCES roles (role_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO users (user_id, password, username, password_recovery_token) 
VALUES (UUID_TO_BIN('15b53dce-32c5-475d-aa1c-7e0fa1682b24'), '$2a$12$Ei0ZtHu1K37/RTMgP97Uiefw5up03sy6dy1EDjlH4wEglrxSi/v3q', 'ronald.liboni@acad.pucrs.br', 'testToken');

INSERT INTO users_roles (user_model_user_id, roles_role_name)
VALUES (UUID_TO_BIN('15b53dce-32c5-475d-aa1c-7e0fa1682b24'), 'ROLE_ADMIN');
