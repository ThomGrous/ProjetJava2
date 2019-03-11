CREATE TABLE `person` (
 `idperson` INT NOT NULL AUTO_INCREMENT,
 `lastname` VARCHAR(45) NOT NULL,
 `firstname` VARCHAR(45) NOT NULL,
 `nickname` VARCHAR(45) NOT NULL,
 `phone_number` VARCHAR(15) NULL,
 `address` VARCHAR(200) NULL,
 `email_address` VARCHAR(150) NULL,
 `birth_date` DATE NULL,
 PRIMARY KEY (`idperson`));
