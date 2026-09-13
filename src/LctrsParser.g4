/*
 * File containing the parsing rules for the small python to LCTRS program created
 * as the final project by Andrew Harrison
 */
parser grammar LctrsParser;

options {
    tokenVocab = LctrsLexer;
}

funcdef:
    DEF IDENTIFIER OPEN_PAREN parameters? CLOSE_PAREN ARROW vartype COLON body
    ;

parameters:
    parameter (COMMA parameter)*
    ;

parameter:
    IDENTIFIER COLON vartype
    ;

vartype:
    INT
    ;

body:
    NEWLINE INDENT stmt+ DEDENT?
    ;

stmt:
    (assignment
    | return_stmt) NEWLINE
    |compound_stmt
    ;

compound_stmt:
    if_stmt
    | while_stmt
    ;

if_stmt:
    IF constraint COLON body (ELSE COLON body)?
    ;

while_stmt:
    WHILE constraint COLON body
    ;

constraint:
    expr comparator expr
    | OPEN_PAREN expr comparator expr CLOSE_PAREN
    ;

comparator:
    EQUALS
    | NOT_EQUALS
    | LESS_THAN
    | GREATER_THAN
    | LT_EQ
    | GT_EQ
    ;

assignment:
    IDENTIFIER ASSIGN expr
    ;

expr:
    expr operation=(MUL|IDIV|MOD) expr
    | expr operation=(ADD|SUB) expr
    | IDENTIFIER
    | INTEGER
    ;

return_stmt:
    RETURN expr
    ;