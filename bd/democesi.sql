-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: democesi
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias` (
  `id_categoria` int NOT NULL AUTO_INCREMENT,
  `descripcion_categoria` text,
  PRIMARY KEY (`id_categoria`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,'a'),(2,'Acoso Sexual'),(3,'Violencia Domestica'),(4,'Drogas'),(5,'Educacion Sexual'),(6,'Bullying'),(7,'Cyber Bullying');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jornadas`
--

DROP TABLE IF EXISTS `jornadas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jornadas` (
  `id_jornada` int NOT NULL AUTO_INCREMENT,
  `objetivo_jornada` text,
  `titulo_jornada` text,
  PRIMARY KEY (`id_jornada`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jornadas`
--

LOCK TABLES `jornadas` WRITE;
/*!40000 ALTER TABLE `jornadas` DISABLE KEYS */;
/*!40000 ALTER TABLE `jornadas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jornadas_materiales`
--

DROP TABLE IF EXISTS `jornadas_materiales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jornadas_materiales` (
  `id_jornadaMaterial` int NOT NULL AUTO_INCREMENT,
  `id_jornada` int DEFAULT NULL,
  `id_material` int DEFAULT NULL,
  PRIMARY KEY (`id_jornadaMaterial`),
  KEY `fk_jornadaMateriales_jornadas` (`id_jornada`),
  KEY `fk_jornadaMateriales_materiales` (`id_material`),
  CONSTRAINT `fk_jornadaMateriales_jornadas` FOREIGN KEY (`id_jornada`) REFERENCES `jornadas` (`id_jornada`),
  CONSTRAINT `fk_jornadaMateriales_materiales` FOREIGN KEY (`id_material`) REFERENCES `materiales` (`id_material`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jornadas_materiales`
--

LOCK TABLES `jornadas_materiales` WRITE;
/*!40000 ALTER TABLE `jornadas_materiales` DISABLE KEYS */;
/*!40000 ALTER TABLE `jornadas_materiales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `materiales`
--

DROP TABLE IF EXISTS `materiales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `materiales` (
  `id_material` int NOT NULL AUTO_INCREMENT,
  `descripcion_material` text,
  `fuente_material` text,
  `enlace_material` text,
  `procedencia_material` text,
  `prioridad_material` tinyint DEFAULT NULL,
  `categoria_material` int DEFAULT NULL,
  `titulo_material` varchar(100) NOT NULL,
  PRIMARY KEY (`id_material`),
  UNIQUE KEY `titulo` (`titulo_material`),
  UNIQUE KEY `titulo_material` (`titulo_material`),
  UNIQUE KEY `titulo_material_2` (`titulo_material`),
  KEY `fk_materiales_categorias` (`categoria_material`),
  CONSTRAINT `fk_materiales_categorias` FOREIGN KEY (`categoria_material`) REFERENCES `categorias` (`id_categoria`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materiales`
--

LOCK TABLES `materiales` WRITE;
/*!40000 ALTER TABLE `materiales` DISABLE KEYS */;
INSERT INTO `materiales` VALUES (39,'ddsf','dsfdsf','dfssd',NULL,0,4,'fasdf'),(40,'saddsa','sadsda','sadsad','ddsadsa',1,1,'sadsad'),(41,'gg','gg','gg',NULL,0,3,'gg'),(42,'dsa','da','ads','asd',1,4,'sda');
/*!40000 ALTER TABLE `materiales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `materiales_propuestas`
--

DROP TABLE IF EXISTS `materiales_propuestas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `materiales_propuestas` (
  `id_materialPropuesta` int NOT NULL AUTO_INCREMENT,
  `id_material` int DEFAULT NULL,
  `id_propuesta` int DEFAULT NULL,
  PRIMARY KEY (`id_materialPropuesta`),
  KEY `fk_materialesPropuestas_materiales` (`id_material`),
  KEY `fk_materialesPropuestas_propuestas` (`id_propuesta`),
  CONSTRAINT `fk_materialesPropuestas_materiales` FOREIGN KEY (`id_material`) REFERENCES `materiales` (`id_material`),
  CONSTRAINT `fk_materialesPropuestas_propuestas` FOREIGN KEY (`id_propuesta`) REFERENCES `propuestas` (`id_propuesta`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materiales_propuestas`
--

LOCK TABLES `materiales_propuestas` WRITE;
/*!40000 ALTER TABLE `materiales_propuestas` DISABLE KEYS */;
INSERT INTO `materiales_propuestas` VALUES (25,41,14),(26,41,16),(27,41,17),(28,41,17),(29,41,14);
/*!40000 ALTER TABLE `materiales_propuestas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `propuestas`
--

DROP TABLE IF EXISTS `propuestas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `propuestas` (
  `id_propuesta` int NOT NULL AUTO_INCREMENT,
  `origen_propuesta` text,
  `estado_propuesta` text,
  `autor_propuesta` text,
  `fecha_propuesta` date DEFAULT NULL,
  `descripcion_propuesta` text,
  `motivacion_propuesta` text,
  `motivo_propuesta` text,
  `id_categoria_propuesta` int NOT NULL,
  `titulo_propuesta` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_propuesta`),
  UNIQUE KEY `titulo_propuesta` (`titulo_propuesta`),
  KEY `fk_propuesta_categorias` (`id_categoria_propuesta`),
  CONSTRAINT `fk_propuesta_categorias` FOREIGN KEY (`id_categoria_propuesta`) REFERENCES `categorias` (`id_categoria`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `propuestas`
--

LOCK TABLES `propuestas` WRITE;
/*!40000 ALTER TABLE `propuestas` DISABLE KEYS */;
INSERT INTO `propuestas` VALUES (5,'a','aprobado','a','2022-06-02','22','222',NULL,1,'22'),(7,'si','aprobado','Ariel Marquez','2022-06-01','as','as',NULL,4,'as'),(8,'asdad','aprobado','asdasd','2022-11-06','asasd','asdasd',NULL,1,'sexo'),(9,'4','aprobado','4','2019-01-04','4','1',NULL,6,'5'),(10,'asdasd','aprobado','saas','2022-11-09','asdasdsda','asdasdasd',NULL,1,'tontoooo'),(11,'ser pelotudo','aprobado','Pedro Haro','2022-11-11','me dicen pero haro','suicidio colectivo en el colectivo 1',NULL,2,'pero'),(12,'el pepe','aprobado','Liam','2022-11-11','ete sech','pedrotudo',NULL,6,'eso tilin'),(13,'Estudiante','aprobado','Yo','2022-11-11','Mi pija con peluca','las pijas',NULL,1,'Luca'),(14,'sadadssad','aprobado','adsad','2022-11-11','4324','23423',NULL,3,'432423'),(15,'asdf','aprobado','asdf','2022-11-11','holas','hola',NULL,1,'asdf'),(16,'312312','aprobado','543','2022-11-13','312312','123123',NULL,3,'312312312312'),(17,'h','aprobado','6','2022-11-11','h','j',NULL,3,'j'),(18,'t','aprobado','t','2022-11-11','y','y',NULL,2,'t'),(19,'arielito','aprobado','arielito','2022-11-13','arielito','arielito',NULL,1,'arielito'),(20,'dasdas','aprobado','adasd','2022-11-11','6767','7676',NULL,5,'9876'),(21,'gf','aprobado','adas','2022-12-11','gfgf','fgfg',NULL,4,'5hg'),(22,'3','aprobado','4','2022-05-05','n','n',NULL,1,'n'),(23,'btbt','aprobado','dasdas','2022-11-13','btbt','btbt',NULL,7,'btbt'),(24,'dsad','aprobado','iioio','2022-12-11','iooi','iooi',NULL,5,'ioioio');
/*!40000 ALTER TABLE `propuestas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-09 21:02:40
