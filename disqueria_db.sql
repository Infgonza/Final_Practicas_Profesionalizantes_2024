-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-10-2024 a las 17:47:17
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `disqueria_db`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carrito_de_compra`
--

CREATE TABLE `carrito_de_compra` (
  `id_carrito_de_compra` bigint(20) NOT NULL,
  `precio_unitario` double DEFAULT NULL,
  `subtotal` double DEFAULT NULL,
  `usuario_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `carrito_de_compra`
--

INSERT INTO `carrito_de_compra` (`id_carrito_de_compra`, `precio_unitario`, `subtotal`, `usuario_id`) VALUES
(1, NULL, NULL, 4),
(2, NULL, NULL, 1),
(3, NULL, NULL, 9);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id_cliente` bigint(20) NOT NULL,
  `apellido` varchar(255) DEFAULT NULL,
  `dni` int(11) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `fk_carrito` bigint(20) DEFAULT NULL,
  `fk_domicilio` bigint(20) DEFAULT NULL,
  `fk_usuario` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compra`
--

CREATE TABLE `compra` (
  `id_compra` bigint(20) NOT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `fk_carrito` bigint(20) DEFAULT NULL,
  `fk_cliente` bigint(20) DEFAULT NULL,
  `fk_factura` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_compra`
--

CREATE TABLE `detalle_compra` (
  `id_detalle_compra` bigint(20) NOT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `precio_unitario` double DEFAULT NULL,
  `fk_compra` bigint(20) DEFAULT NULL,
  `fk_producto` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_factura`
--

CREATE TABLE `detalle_factura` (
  `id_detalle_factura` bigint(20) NOT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `precio_unitario` double DEFAULT NULL,
  `fk_factura` bigint(20) DEFAULT NULL,
  `fk_producto` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `domicilios`
--

CREATE TABLE `domicilios` (
  `id_domicilio` bigint(20) NOT NULL,
  `calle` varchar(255) DEFAULT NULL,
  `cod_postal` int(11) DEFAULT NULL,
  `numero` int(11) DEFAULT NULL,
  `provincia` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura`
--

CREATE TABLE `factura` (
  `id_factura` bigint(20) NOT NULL,
  `fecha_emision` date DEFAULT NULL,
  `monto_total` double DEFAULT NULL,
  `fk_cliente` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `item_carrito`
--

CREATE TABLE `item_carrito` (
  `id` bigint(20) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `carrito_id` bigint(20) DEFAULT NULL,
  `producto_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `item_carrito`
--

INSERT INTO `item_carrito` (`id`, `cantidad`, `carrito_id`, `producto_id`) VALUES
(4, 1, 2, 2),
(5, 1, 3, 1),
(6, 1, 2, 1),
(10, 1, 1, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `tipo_producto` varchar(31) NOT NULL,
  `id_producto` bigint(20) NOT NULL,
  `descripción` varchar(255) DEFAULT NULL,
  `imagen_url` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `precio` double DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `artista` varchar(255) DEFAULT NULL,
  `fecha_lanzamiento` varchar(255) DEFAULT NULL,
  `genero` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`tipo_producto`, `id_producto`, `descripción`, `imagen_url`, `nombre`, `precio`, `stock`, `artista`, `fecha_lanzamiento`, `genero`) VALUES
('Vinilo', 1, 'trench', 'https://aws-groove-records.s3.amazonaws.com/trench.png', 'Trench', 21000, 6, 'Twenty One Pilots', '2018', 'Rock Alternativo'),
('Vinilo', 2, 'igor', 'https://aws-groove-records.s3.amazonaws.com/igor.png', 'Igor', 23000, 5, 'Tyler, The Creator', '2019', 'Hip Hop'),
('Vinilo', 5, 'vinilo doble', 'https://aws-groove-records.s3.amazonaws.com/kfad.png', 'King for a Day... Fool for a Lifetime', 40000, 4, 'Faith No More', '1995', 'Metal Alternativo'),
('Disco', 6, 'Cd estándar', 'https://aws-groove-records.s3.amazonaws.com/midnight.png', 'Midnight Memories', 28008, 8, 'One Direction', '2013', 'Pop rock'),
('Disco', 7, 'cd estandar oktubre\r\n', 'https://aws-groove-records.s3.amazonaws.com/oktubre.png', 'Oktubre', 24105, 5, 'Patricio Rey y sus Redonditos de Ricota', '1986', 'Hard Rock');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL,
  `nombre_rol` enum('Usuario','Administrador') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`id`, `nombre_rol`) VALUES
(1, 'Usuario'),
(2, 'Administrador');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_usuario` bigint(20) NOT NULL,
  `contrasenia` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nombre_usuario` varchar(255) DEFAULT NULL,
  `rol` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `contrasenia`, `email`, `nombre_usuario`, `rol`) VALUES
(1, '$2a$10$blwGaipETsYOAzA5iP8ZjOMp8.mYmDHkA3UnEfG/YTJVlmqAu5tIC', 'user@gmail.com', 'user', NULL),
(2, '$2a$10$ntbH/mDmbS.mRgF0FxVXeuXAU.AofI5.uRD484cJyNmCigvc0saKK', 'user2@gmail.com', 'user2', NULL),
(4, '$2a$10$yK69vJ6GVHSCWAotpnd4tOr3jn073v1TKa/BMB93i46IiuXKWDvsK', 'admin@gmail.com', 'admin', NULL),
(5, '$2a$10$UsHzFACww2hRWd7wwcLJjeMmzelk0Pg/.BagoxpmczLTQEpJbtsOK', 'user3@gmail.com', 'user3', NULL),
(6, '$2a$10$LYWgNNnZu0M1PU2rQ2TuUeaXcN29JDWB6Kv4KbELFpvi/sMEBCuWS', 'user4@gmail.com', 'user4', NULL),
(7, '$2a$10$IYFxWXPjFY.zgrv3KyfibemMQr3yZyT/gwyGAMGBYNtPl5gCJX79S', 'user5@gmail.com', 'user5', NULL),
(8, '$2a$10$O4/ZXl6VarVy708O/cBjDOiq/DH4emV.K.3yewKJSgYYq52jj7WGG', 'user6@gmail.com', 'user6', NULL),
(9, '$2a$10$/NC68XT9p2zwKmBUZpKGQePD5VuezjW.0Jq6M28Md2OgoXw9W44bW', 'user22@gmail.com', 'user22', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios_roles`
--

CREATE TABLE `usuarios_roles` (
  `usuario_id` bigint(20) NOT NULL,
  `rol_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios_roles`
--

INSERT INTO `usuarios_roles` (`usuario_id`, `rol_id`) VALUES
(1, 1),
(2, 1),
(4, 2),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `carrito_de_compra`
--
ALTER TABLE `carrito_de_compra`
  ADD PRIMARY KEY (`id_carrito_de_compra`),
  ADD UNIQUE KEY `UKb3vmf1fm6ddudrwiyquov6mty` (`usuario_id`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id_cliente`),
  ADD UNIQUE KEY `UKm6ysdwsqke00e5piajbvgn6lg` (`dni`),
  ADD UNIQUE KEY `UKilf62l6sqhpjcm7tguhyrp3g5` (`fk_carrito`),
  ADD UNIQUE KEY `UK9ipb4dli0p8loa618wksyb2px` (`fk_domicilio`),
  ADD UNIQUE KEY `UK34qic66cfyrqwvngonrsv8tei` (`fk_usuario`);

--
-- Indices de la tabla `compra`
--
ALTER TABLE `compra`
  ADD PRIMARY KEY (`id_compra`),
  ADD UNIQUE KEY `UK9ygw77251j101e85u9qxvuy2b` (`fk_carrito`),
  ADD UNIQUE KEY `UKlytu6bbt781arj153h5ybdybp` (`fk_factura`),
  ADD KEY `FK4ed2od56tl6299plg5q2poln1` (`fk_cliente`);

--
-- Indices de la tabla `detalle_compra`
--
ALTER TABLE `detalle_compra`
  ADD PRIMARY KEY (`id_detalle_compra`),
  ADD KEY `FKjhm4nglp8bkdyw2fmx0nyoc16` (`fk_compra`),
  ADD KEY `FKqsthjni5f5s25ub9x6njk6u94` (`fk_producto`);

--
-- Indices de la tabla `detalle_factura`
--
ALTER TABLE `detalle_factura`
  ADD PRIMARY KEY (`id_detalle_factura`),
  ADD KEY `FKpivyynb1ekrqvoej3r9tsy2ms` (`fk_factura`),
  ADD KEY `FKtg9uykwdsdr2279qarigq1x9v` (`fk_producto`);

--
-- Indices de la tabla `domicilios`
--
ALTER TABLE `domicilios`
  ADD PRIMARY KEY (`id_domicilio`);

--
-- Indices de la tabla `factura`
--
ALTER TABLE `factura`
  ADD PRIMARY KEY (`id_factura`),
  ADD KEY `FKfd8s1qip8sypr76iwmqc6jp60` (`fk_cliente`);

--
-- Indices de la tabla `item_carrito`
--
ALTER TABLE `item_carrito`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKaoihe5fjmlk7sm8vg7kc1wx68` (`carrito_id`),
  ADD KEY `FKgarw89vvyxd65d4bqvwwmobud` (`producto_id`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id_producto`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `UKkfsp0s1tflm1cwlj8idhqsad0` (`email`),
  ADD UNIQUE KEY `UKof5vabgukahdwmgxk4kjrbu98` (`nombre_usuario`);

--
-- Indices de la tabla `usuarios_roles`
--
ALTER TABLE `usuarios_roles`
  ADD PRIMARY KEY (`usuario_id`,`rol_id`),
  ADD KEY `FK5338ehgluufgc8bpj08nrq970` (`rol_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `carrito_de_compra`
--
ALTER TABLE `carrito_de_compra`
  MODIFY `id_carrito_de_compra` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id_cliente` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `compra`
--
ALTER TABLE `compra`
  MODIFY `id_compra` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `detalle_compra`
--
ALTER TABLE `detalle_compra`
  MODIFY `id_detalle_compra` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `detalle_factura`
--
ALTER TABLE `detalle_factura`
  MODIFY `id_detalle_factura` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `domicilios`
--
ALTER TABLE `domicilios`
  MODIFY `id_domicilio` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `factura`
--
ALTER TABLE `factura`
  MODIFY `id_factura` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `item_carrito`
--
ALTER TABLE `item_carrito`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id_producto` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usuario` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `carrito_de_compra`
--
ALTER TABLE `carrito_de_compra`
  ADD CONSTRAINT `FK6q09rko8c3d6jo1vg1k417qux` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id_usuario`);

--
-- Filtros para la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD CONSTRAINT `FKf8ttl70h8yrodfrahmr529ql7` FOREIGN KEY (`fk_usuario`) REFERENCES `usuarios` (`id_usuario`),
  ADD CONSTRAINT `FKninrxh6nbrjgul4vplohxguvy` FOREIGN KEY (`fk_domicilio`) REFERENCES `domicilios` (`id_domicilio`),
  ADD CONSTRAINT `FKpsnjhjqwlhd9tmc7t7ik428e6` FOREIGN KEY (`fk_carrito`) REFERENCES `carrito_de_compra` (`id_carrito_de_compra`);

--
-- Filtros para la tabla `compra`
--
ALTER TABLE `compra`
  ADD CONSTRAINT `FK4ed2od56tl6299plg5q2poln1` FOREIGN KEY (`fk_cliente`) REFERENCES `clientes` (`id_cliente`),
  ADD CONSTRAINT `FK4tlh0cj9uefda2qoordsj00mi` FOREIGN KEY (`fk_carrito`) REFERENCES `carrito_de_compra` (`id_carrito_de_compra`),
  ADD CONSTRAINT `FKb3j5p8d43f7xy7e5r047jjvp4` FOREIGN KEY (`fk_factura`) REFERENCES `factura` (`id_factura`);

--
-- Filtros para la tabla `detalle_compra`
--
ALTER TABLE `detalle_compra`
  ADD CONSTRAINT `FKjhm4nglp8bkdyw2fmx0nyoc16` FOREIGN KEY (`fk_compra`) REFERENCES `compra` (`id_compra`),
  ADD CONSTRAINT `FKqsthjni5f5s25ub9x6njk6u94` FOREIGN KEY (`fk_producto`) REFERENCES `producto` (`id_producto`);

--
-- Filtros para la tabla `detalle_factura`
--
ALTER TABLE `detalle_factura`
  ADD CONSTRAINT `FKpivyynb1ekrqvoej3r9tsy2ms` FOREIGN KEY (`fk_factura`) REFERENCES `factura` (`id_factura`),
  ADD CONSTRAINT `FKtg9uykwdsdr2279qarigq1x9v` FOREIGN KEY (`fk_producto`) REFERENCES `producto` (`id_producto`);

--
-- Filtros para la tabla `factura`
--
ALTER TABLE `factura`
  ADD CONSTRAINT `FKfd8s1qip8sypr76iwmqc6jp60` FOREIGN KEY (`fk_cliente`) REFERENCES `clientes` (`id_cliente`);

--
-- Filtros para la tabla `item_carrito`
--
ALTER TABLE `item_carrito`
  ADD CONSTRAINT `FKaoihe5fjmlk7sm8vg7kc1wx68` FOREIGN KEY (`carrito_id`) REFERENCES `carrito_de_compra` (`id_carrito_de_compra`),
  ADD CONSTRAINT `FKgarw89vvyxd65d4bqvwwmobud` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id_producto`);

--
-- Filtros para la tabla `usuarios_roles`
--
ALTER TABLE `usuarios_roles`
  ADD CONSTRAINT `FK5338ehgluufgc8bpj08nrq970` FOREIGN KEY (`rol_id`) REFERENCES `roles` (`id`),
  ADD CONSTRAINT `FKqcxu02bqipxpr7cjyj9dmhwec` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id_usuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
