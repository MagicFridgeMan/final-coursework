/*
 * File containing the parsing rules for the small python to LCTRS program created
 * as the final project by Andrew Harrison
 */
parser grammar LctrsParser;

options {
    tokenVocab = LctrsLexer;
}

// def func_name (parameters) -> returnType: body
funcdef:
    DEF IDENTIFIER OPEN_PAREN paramlist CLOSE_PAREN ARROW vartype COLON body
    ;

paramlist:
    parameter
    | parameter (COMMA parameter)*
    |
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
    assigment
    | compound_stmt
    | return_stmt
    ;

compound_stmt:
    if_stmt
    while_stmt
    ;

if_stmt:
    IF test COLON body (ELSE COLON body)?
    ;

while_stmt:
    WHILE test COLON body
    ;

test:
    expr comparator expr
    ;

comparator:
    EQUALS
    | NOT_EQUALS
    | LESS_THAN
    | GREATER_THAN
    | LT_EQ
    | GT_EQ
    ;

assigment:
    IDENTIFIER ASSIGN expr
    ;

expr:
    expr operation=(MUL|IDIV|MOD) expr
    | expr operation=(ADD|SUB) expr
    | test
    | IDENTIFIER
    | INTEGER
    ;

return_stmt:
    RETURN expr
    ;