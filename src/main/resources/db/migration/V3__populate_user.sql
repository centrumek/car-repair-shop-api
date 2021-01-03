INSERT INTO user
VALUES (random_uuid(), current_timestamp(), current_timestamp(),
        'customer@example.com',
        '$2y$12$vSxwTBLj6ibQhrM7hCr7Juy8SfEgPZ3Ry7K/gKL3IqJTYw/tI.S06', -- as email
        'CUSTOMER', 'John', 'Snow', '1234567890'),
       (random_uuid(), current_timestamp(), current_timestamp(),
        'employee@example.com',
        '$2y$12$m6AUIyOPxbLZ09IFShehcOBoEYvNlGog0PBi6KSzToiHp67cvQceS', -- as email
        'EMPLOYEE', 'Mark', 'Carnegie', '4321876590'),
       (random_uuid(), current_timestamp(), current_timestamp(),
        'head@example.com',
        '$2y$12$FRglP6pRDuRAoXBYDrfp.Oyv21n9FTnmy6XXxPIyqphjyDbD3a/hS', -- as email
        'HEAD', 'Tom', 'Potter', '6543217890')