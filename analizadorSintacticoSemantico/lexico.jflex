/*
Autores:      Cabrera Gaytán Jazmín Andrea
              Camacho Morales Gerardo Iván
              Nicolas Marin Brian Geovanny
              Lázaro Martínez Abraham Josué
              Oropeza Castañeda Ángel Eduardo

Versión:      1.1
Fecha:        20 de diciembre de 2020
Nombre:       lexico.jflex
*/
import java.util.*;

%%
%public
%class Lexer
%line
%int 
%eofval{
  //Al encontrar el fin de archivo, regresa un valor 0
  return 0;
%eofval}

%{
//Definición de constantes para definir que número regresará el 
//analizador para cada token
private static final int PACKAGE=1;
private static final int VAR=2;
private static final int CONST=3;
private static final int UINT8=4;
private static final int UINT16=5;
private static final int INT8=6;
private static final int INT16=7;
private static final int INT32=8;
private static final int FLOAT32=9;
private static final int FLOAT64=10;
private static final int COMPLEX64=11;
private static final int BYTE=12;
private static final int STRING=13;
private static final int BOOL=14;
private static final int STRUCT=15;
private static final int FUNC=16;
private static final int DEFER=17;
private static final int IF=18;
private static final int ELSE=19;
private static final int SWITCH=20;
private static final int CASE=21;
private static final int DEFAULT=22;
private static final int FOR=23;
private static final int RETURN=24;
private static final int BREAK=25;
private static final int CONTINUE=26;
private static final int OR=27;
private static final int AND=28;
private static final int IGUAL=29;
private static final int DESIGUAL=30;
private static final int MT=31;
private static final int MET=32;
private static final int LT=33;
private static final int LET=34;
private static final int SUMA=35;
private static final int RESTA=36;
private static final int MULTI=37;
private static final int DIVISION=38;
private static final int MODULO=39;
private static final int NEGACION=40;
private static final int DIR=41;
private static final int INCREMENTO=42;
private static final int DECREMENTO=43;
private static final int ASIGNACION=44;
private static final int P1=45;
private static final int P2=46;
private static final int C1=47;
private static final int C2=48;
private static final int L1=49;
private static final int L2=50;
private static final int PUNTO=51;
private static final int DOSPUNTOS=52;
private static final int PUNTOYCOMA=53;
private static final int COMA=54;
private static final int IDENTIFIER=55;
private static final int INT_LIT=56;
private static final int FLOAT_LIT=57;
private static final int IMAGINARY_LIT=58;
private static final int STRING_LIT=59;
private static final int BOOL_LIT=60;
private static final int INT=61;
private static final int FLOAT=62;

public String linea = "";

public int getYyline(){
  return this.yyline;
}
public void anexar(){
  linea+=yytext();
}
public void limpiar(){
  linea = "";
}
%}
 
/* Inicio de Expresiones regulares */

letra=[a-zA-Z_]
digito=[0-9]

//IDENTIFICADOR
identifier={letra}({letra}|{digito})*

//INT
int_lit={digito}([_]?{digito})*

//FLOAT
exp=([eE][+-]?{int_lit}+)
float_lit=((({int_lit})*\.({digito})+|({int_lit})+\.({digito})*){exp}?)|({int_lit})+({exp})

//IMAGINARIOS
imaginary_lit=({float_lit}|{int_lit})i

//STRING
comillas1=\'((\\[uxnU\"\'\\])*([^\\\'])*)*\'
comillas2=\"((\\[uxnU\"\'\\])*([^\\\"])*)*\"
string_lit={comillas1}|{comillas2}

//BOOLEANOS
bool_lit=(true|false)

//ESPACIOS - IGNORAR
esps=[ \r\t]
enter=[\n]

//PALABRAS RESERVADAS
PACKAGE=package
VAR=var
CONST=const
UINT8=uint8
UINT16=uint16
INT=int
INT8=int8
INT16=int16
INT32=int32
FLOAT=float
FLOAT32=float32
FLOAT64=float64
COMPLEX64=complex64
BYTE=byte
STRING=string
BOOL=bool
STRUCT=struct
FUNC=func
DEFER=defer
IF=if
ELSE=else
SWITCH=switch
CASE=case
DEFAULT=default
FOR=for
RETURN=return
BREAK=break
CONTINUE=continue

//OPERADORES
OR="||"
AND="&&"
IGUAL="=="
DESIGUAL="!="
MT=">"
MET=">="
LT="<"
LET="<="
SUMA="+"
RESTA="-"
MULTI="*"
DIVISION="/"
MODULO="%"
NEGACION="!"
DIR="&"
INCREMENTO="++"
DECREMENTO="--"
ASIGNACION="="

//PUNTUACIÓN 
P1="("
P2=")"
C1="["
C2="]"
L1="{"
L2="}"
PUNTO="."
DOSPUNTOS=":"
PUNTOYCOMA=";"
COMA=","

%%
{esps} {/*No se hace nada*/}
{enter} {limpiar();}

{PACKAGE} {anexar();return PACKAGE;}
{VAR} {anexar();return VAR;}
{CONST} {anexar();return CONST;}
{UINT8} {anexar();return UINT8;}
{UINT16} {anexar();return UINT16;}
{INT} {anexar();return INT;}
{INT8} {anexar();return INT8;}
{INT16} {anexar();return INT16;}
{INT32} {anexar();return INT32;}
{FLOAT} {anexar();return FLOAT;}
{FLOAT32} {anexar();return FLOAT32;}
{FLOAT64} {anexar();return FLOAT64;}
{COMPLEX64} {anexar();return COMPLEX64;}
{BYTE} {anexar();return BYTE;}
{STRING} {anexar();return STRING;}
{BOOL} {anexar();return BOOL;}
{STRUCT} {anexar();return STRUCT;}
{FUNC} {anexar();return FUNC;}
{DEFER} {anexar();return DEFER;}
{IF} {anexar();return IF;}
{ELSE} {anexar();return ELSE;}
{SWITCH} {anexar();return SWITCH;}
{CASE} {anexar();return CASE;}
{DEFAULT} {anexar();return DEFAULT;}
{FOR} {anexar();return FOR;}
{RETURN} {anexar();return RETURN;}
{BREAK} {anexar();return BREAK;}
{CONTINUE} {anexar();return CONTINUE;}

{bool_lit} {anexar();return BOOL_LIT;}

{identifier} {anexar();return IDENTIFIER;}

{int_lit} {anexar();return INT_LIT;}

{float_lit} {anexar();return FLOAT_LIT;}

{imaginary_lit} {anexar();return IMAGINARY_LIT;}

{string_lit} {anexar();return STRING_LIT;}

{OR} {anexar();return OR;}
{AND} {anexar();return AND;}
{IGUAL} {anexar();return IGUAL;}
{DESIGUAL} {anexar();return DESIGUAL;}
{MT} {anexar();return MT;}
{MET} {anexar();return MET;}
{LT} {anexar();return LT;}
{LET} {anexar();return LET;}
{SUMA} {anexar();return SUMA;}
{RESTA} {anexar();return RESTA;}
{MULTI} {anexar();return MULTI;}
{DIVISION} {anexar();return DIVISION;}
{MODULO} {anexar();return MODULO;}
{NEGACION} {anexar();return NEGACION;}
{DIR} {anexar();return DIR;}
{INCREMENTO} {anexar();return INCREMENTO;}
{DECREMENTO} {anexar();return DECREMENTO;}
{ASIGNACION} {anexar();return ASIGNACION;}
{P1} {anexar();return P1;}
{P2} {anexar();return P2;}
{C1} {anexar();return C1;}
{C2} {anexar();return C2;}
{L1} {anexar();return L1;}
{L2} {anexar();return L2;}
{PUNTO} {anexar();return PUNTO;}
{DOSPUNTOS} {anexar();return DOSPUNTOS;}
{PUNTOYCOMA} {anexar();return PUNTOYCOMA;}
{COMA} {anexar();return COMA;}

. {anexar();return -1;}