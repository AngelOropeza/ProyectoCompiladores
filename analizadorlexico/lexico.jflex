/*
Autores:      Cabrera Gaytán Jazmín Andrea
              Camacho Morales Gerardo Iván

              Lázaro Martínez Abraham Josué
              Oropeza Castañeda Ángel Eduardo

Versión:      1
Fecha:        13 de noviembre de 2020
Nombre:       Implementación del analizador léxico
*/
import java.util.*;

%%
%public
%class Lexer
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
esps=[ \n\r\t]

//PALABRAS RESERVADAS
PACKAGE=package
VAR=var
CONST=const
UINT8=uint8
UINT16=uint16
INT8=int8
INT16=int16
INT32=int32
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

{PACKAGE} {return PACKAGE;}
{VAR} {return VAR;}
{CONST} {return CONST;}
{UINT8} {return UINT8;}
{UINT16} {return UINT16;}
{INT8} {return INT8;}
{INT16} {return INT16;}
{INT32} {return INT32;}
{FLOAT32} {return FLOAT32;}
{FLOAT64} {return FLOAT64;}
{COMPLEX64} {return COMPLEX64;}
{BYTE} {return BYTE;}
{STRING} {return STRING;}
{BOOL} {return BOOL;}
{STRUCT} {return STRUCT;}
{FUNC} {return FUNC;}
{DEFER} {return DEFER;}
{IF} {return IF;}
{ELSE} {return ELSE;}
{SWITCH} {return SWITCH;}
{CASE} {return CASE;}
{DEFAULT} {return DEFAULT;}
{FOR} {return FOR;}
{RETURN} {return RETURN;}
{BREAK} {return BREAK;}
{CONTINUE} {return CONTINUE;}

{bool_lit} {return BOOL_LIT;}

{identifier} {return IDENTIFIER;}

{int_lit} {return INT_LIT;}

{float_lit} {return FLOAT_LIT;}

{imaginary_lit} {return IMAGINARY_LIT;}

{string_lit} {return STRING_LIT;}

{OR} {return OR;}
{AND} {return AND;}
{IGUAL} {return IGUAL;}
{DESIGUAL} {return DESIGUAL;}
{MT} {return MT;}
{MET} {return MET;}
{LT} {return LT;}
{LET} {return LET;}
{SUMA} {return SUMA;}
{RESTA} {return RESTA;}
{MULTI} {return MULTI;}
{DIVISION} {return DIVISION;}
{MODULO} {return MODULO;}
{NEGACION} {return NEGACION;}
{DIR} {return DIR;}
{INCREMENTO} {return INCREMENTO;}
{DECREMENTO} {return DECREMENTO;}
{ASIGNACION} {return ASIGNACION;}
{P1} {return P1;}
{P2} {return P2;}
{C1} {return C1;}
{C2} {return C2;}
{L1} {return L1;}
{L2} {return L2;}
{PUNTO} {return PUNTO;}
{DOSPUNTOS} {return DOSPUNTOS;}
{PUNTOYCOMA} {return PUNTOYCOMA;}
{COMA} {return COMA;}

. {return -1;}