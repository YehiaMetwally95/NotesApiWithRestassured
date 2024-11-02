CREATE DATABASE  IF NOT EXISTS `automationexercise` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `automationexercise`;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: automationexercise
-- ------------------------------------------------------
-- Server version	9.0.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `Id` int NOT NULL,
  `Name` varchar(100) DEFAULT NULL,
  `Brand` varchar(100) DEFAULT NULL,
  `Category` varchar(100) DEFAULT NULL,
  `Subcategory` varchar(100) DEFAULT NULL,
  `Price` varchar(100) DEFAULT NULL,
  `Availability` varchar(100) CHARACTER SET armscii8 COLLATE armscii8_general_ci DEFAULT NULL,
  `Situation` varchar(100) DEFAULT NULL,
  `Quantity` varchar(100) DEFAULT NULL,
  `TotalPrice` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Blue Top','Polo','Women','Tops','Rs. 500','In Stock','New','3','1500'),(2,'Men Tshirt','H&M','Men','Tshirts','Rs. 400','In Stock','New','1','400'),(14,'Full Sleeves Top Cherry - Pink','Kookie Kids','Kids','Tops & Shirts','Rs. 679','In Stock','New','3','2037'),(35,'Regular Fit Straight Jeans','H&M','Men','Jeans','Rs. 1200','In Stock','New','5','6000'),(42,'Lace Top For Women','Mast & Harbour','Women','Tops','Rs. 1400','In Stock','New','1','1400');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `Title` varchar(255) DEFAULT NULL,
  `Username` varchar(255) DEFAULT NULL,
  `Email` varchar(255) NOT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `DayofBirth` varchar(255) DEFAULT NULL,
  `MonthofBirth` varchar(255) DEFAULT NULL,
  `YearofBirth` varchar(255) DEFAULT NULL,
  `FirstName` varchar(255) DEFAULT NULL,
  `LastName` varchar(255) DEFAULT NULL,
  `Company` varchar(255) DEFAULT NULL,
  `Address1` varchar(255) DEFAULT NULL,
  `Address2` varchar(255) DEFAULT NULL,
  `Country` varchar(255) DEFAULT NULL,
  `State` varchar(255) DEFAULT NULL,
  `City` varchar(255) DEFAULT NULL,
  `ZipCode` varchar(255) DEFAULT NULL,
  `MobileNumber` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('Mrs','Test2438300','Test2438307@gmail.com','Test@%^2438311','25','January','1983','Kara','Aleta','Lakin, Windler and Hagenes','Suite 905 4648 Ratke Street, Binschester, WY 40946','43953 Fadel Ford, Lake Valerystad, WI 16157','New Zealand','Zemlakhaven','Jerroldfort','72293-2852','2440634'),('Mr','Test2440927','Test2441001@gmail.com','Test@%^2441004','15','October','1967','Jonie','Myles','Schuppe Inc','Apt. 110 144 Mellisa Radial, New Bernetta, AZ 33162-3991','0532 Bayer Summit, Beaborough, MT 45462','New Zealand','New Apolonia','North Drema','91006','2442172'),('Mrs','Test2605552','Test2605560@gmail.com','Test@%^2605562','19','November','1985','Ashlea','Cyrus','Mann, Kemmer and Swaniawski','01727 Streich Greens, Port Vannesa, WV 19951-7409','Apt. 576 586 Johnson Trail, South Leif, CA 44910','United States','Brainmouth','Jacobshaven','46683','2606204'),('Mrs','Test2606768','Test2606870@gmail.com','Test@%^2606876','20','July','1905','Madeline','Brooks','Hermiston Group','Suite 860 37203 Terry Expressway, Alvaroborough, IL 59888','90282 Jacobson Manor, Michalport, VA 57217-7563','United States','West Jason','Spencerfort','55479','2607490'),('Mrs','Test2720747','Test2720749@gmail.com','Test@%^2720751','18','November','2009','Adan','Tobi','Mayer, Murphy and Mraz','4555 Jacobs Gardens, Jacobsonmouth, NE 36201-9420','45894 Reuben Rapid, Bahringerburgh, MI 61287','New Zealand','Kelleyville','Zanetown','53753-5983','2721312'),('Mrs','Test2723113','Test2723117@gmail.com','Test@%^2723127','8','November','1921','Lesli','Marsha','Romaguera, Stehr and Wintheiser','Apt. 086 234 Gerald Drive, Rogahnstad, PA 88132','Suite 536 37441 Ziemann Pass, Wolfftown, MA 64731','India','North Ruthie','New Emoryfort','30959-5791','2723460');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-10  4:27:56
