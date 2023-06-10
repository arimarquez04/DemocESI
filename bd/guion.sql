create database democesi;
use democesi;
create table jornadas(
id_jornada int primary key auto_increment,
objetivo_jornada text,
titulo_jornada text
);
create table materiales(
id_material int primary key auto_increment,
descripcion_material text,
fuente_material text,
enlace_material text,
procedencia_material text,
prioridad_material tinyint,
categoria_material int
);
create table categorias(
id_categoria int primary key auto_increment,
decripcion_categoria  text
);
 create table propuestas(
 id_propuesta int primary key auto_increment,
 origen_propuesta text,
 estado_propuesta text,
 autor_propuesta text,
 fecha_propuesta date,
 descripcion_propuesta text,
 motivacion_propuesta text,
 motivo_propuesta text,
 id_categoria_propuesta int not null
 );
 create table jornadas_materiales(
 id_jornadaMaterial int primary key auto_increment,
 id_jornada int,
 id_material int
 );
 create table materiales_propuestas(
 id_materialPropuesta int primary key auto_increment,
 id_material int,
 id_propuesta int
 );

# realacion materiales ----> categorias
 alter table democesi.materiales
 add constraint fk_materiales_categorias
 foreign key (id_categoria_material)
 references democesi.categorias(id_categoria);
 
 # realacion propuestas ----> categorias
 alter table democesi.propuestas
 add constraint fk_propuesta_categorias
 foreign key (id_categoria_propuesta)
 references democesi.categorias(id_categoria);

# realacion materialesPropuestas ----> materiales
alter table democesi.materiales_propuestas
add constraint fk_materialesPropuestas_materiales
foreign key(id_material)
references materiales(id_material);

# realacion materialesPropuestas ----> propuestas
alter table democesi.materiales_propuestas
add constraint fk_materialesPropuestas_propuestas
foreign key (id_propuesta)
references propuestas(id_propuesta);

# realacion jornadasMateriales ----> jornadas
alter table democesi.jornadas_materiales
add constraint fk_jornadaMateriales_jornadas
foreign key (id_jornada)
references jornadas(id_jornada);

# realacion jornadasMateriales ----> materiales
alter table democesi.jornadas_materiales
add constraint fk_jornadaMateriales_materiales
foreign key (id_material)
references materiales(id_material);
