CREATE TABLE IF NOT EXISTS ticket
(
    uuid              UUID PRIMARY KEY,
    created_at        TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by        UUID                     NOT NULL,
    updated_at        TIMESTAMP WITH TIME ZONE NOT NULL,
    customer_id       UUID                     NOT NULL,
    title             VARCHAR(255)             NOT NULL,
    brand             VARCHAR(255)             NOT NULL,
    model             VARCHAR(255)             NOT NULL,
    description       CLOB                     NOT NULL,
    attached_items    CLOB,
    status            VARCHAR(255)             NOT NULL,
    estimated_price   DECIMAL(20, 2)           NOT NULL,
    estimated_release TIMESTAMP WITH TIME ZONE NOT NULL,
    final_price       DECIMAL(20, 2),
    calculation_note  CLOB,
    released_at       TIMESTAMP WITH TIME ZONE,
    released_by       UUID
);

CREATE index ticket_customer_id_index ON ticket (customer_id);
CREATE index ticket_status_index ON ticket (status);