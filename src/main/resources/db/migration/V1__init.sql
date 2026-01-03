

CREATE TABLE applicants (
  id BIGINT NOT NULL AUTO_INCREMENT,
  full_name VARCHAR(100) NOT NULL,
  full_name_kana VARCHAR(100) NULL,
  email VARCHAR(255) NULL,
  phone VARCHAR(50) NULL,
  postal_code VARCHAR(20) NULL,
  address1 VARCHAR(255) NULL,
  address2 VARCHAR(255) NULL,
  created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  deleted_at DATETIME(6) NULL,
  PRIMARY KEY (id),
  INDEX idx_applicants_deleted_at (deleted_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE subsidy_applications (
  id BIGINT NOT NULL AUTO_INCREMENT,
  applicant_id BIGINT NOT NULL,
  application_date DATE NOT NULL,
  amount_requested BIGINT NOT NULL,
  status VARCHAR(20) NOT NULL,
  decision_date DATE NULL,
  approved_amount BIGINT NULL,
  paid_date DATE NULL,
  note TEXT NULL,
  created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  deleted_at DATETIME(6) NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_applications_applicant
    FOREIGN KEY (applicant_id) REFERENCES applicants(id),
  INDEX idx_applications_applicant_id (applicant_id),
  INDEX idx_applications_status (status),
  INDEX idx_applications_application_date (application_date),
  INDEX idx_applications_deleted_at (deleted_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE documents (
  id BIGINT NOT NULL AUTO_INCREMENT,
  application_id BIGINT NOT NULL,
  document_type VARCHAR(30) NOT NULL,
  document_no VARCHAR(50) NULL,
  issued_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  issued_by VARCHAR(100) NULL,
  payload_json JSON NULL,
  created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  deleted_at DATETIME(6) NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_documents_application
    FOREIGN KEY (application_id) REFERENCES subsidy_applications(id),
  INDEX idx_documents_application_id (application_id),
  INDEX idx_documents_type (document_type),
  INDEX idx_documents_issued_at (issued_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE audit_logs (
  id BIGINT NOT NULL AUTO_INCREMENT,
  actor VARCHAR(100) NOT NULL,
  action VARCHAR(30) NOT NULL,
  entity_type VARCHAR(50) NOT NULL,
  entity_id BIGINT NULL,
  detail_json JSON NULL,
  created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (id),
  INDEX idx_audit_created_at (created_at),
  INDEX idx_audit_entity (entity_type, entity_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
