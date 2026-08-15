/*
* The subset of python rules used within my small LCTRS program.
*/

lexer grammar Python3Lexer;

// All comments that start with "///" are copy-pasted from
// The Python Language Reference

tokens {
    INDENT,
    DEDENT
}

options {
    superClass = Python3LexerBase;
}

// Insert here @header for C++ lexer.

/*
 * lexer rules
 */

STRING: STRING_LITERAL | BYTES_LITERAL;

NUMBER: INTEGER | FLOAT_NUMBER | IMAG_NUMBER;

INTEGER: DECIMAL_INTEGER | OCT_INTEGER | HEX_INTEGER | BIN_INTEGER;


DEF        : 'def';
ELIF       : 'elif';
ELSE       : 'else';
FOR        : 'for';
IF         : 'if';
RETURN     : 'return';
WHILE      : 'while';

NEWLINE: ({this.atStartOfInput()}? SPACES | ( '\r'? '\n' | '\r' | '\f') SPACES?) {this.onNewLine();};

/// identifier   ::=  id_start id_continue*
NAME: ID_START ID_CONTINUE*;

/// decimalinteger ::=  nonzerodigit digit* | "0"+
DECIMAL_INTEGER: NON_ZERO_DIGIT DIGIT* | '0'+;

STAR               : '*';
OPEN_PAREN         : '(' {this.openBrace();};
CLOSE_PAREN        : ')' {this.closeBrace();};
COLON              : ':';
SEMI_COLON         : ';';
POWER              : '**';
ASSIGN             : '=';
ADD                : '+';
MINUS              : '-';
DIV                : '/';
MOD                : '%';
IDIV               : '//';
NOT_OP             : '~';
OPEN_BRACE         : '{' {this.openBrace();};
CLOSE_BRACE        : '}' {this.closeBrace();};
LESS_THAN          : '<';
GREATER_THAN       : '>';
EQUALS             : '==';
GT_EQ              : '>=';
LT_EQ              : '<=';
NOT_EQ_1           : '<>';
NOT_EQ_2           : '!=';
ADD_ASSIGN         : '+=';
SUB_ASSIGN         : '-=';
MULT_ASSIGN        : '*=';
DIV_ASSIGN         : '/=';
MOD_ASSIGN         : '%=';
IDIV_ASSIGN        : '//=';

SKIP_: ( SPACES | COMMENT | LINE_JOINING) -> skip;

UNKNOWN_CHAR: .;

/*
 * fragments
 */

/// digit          ::=  "0"..."9"
fragment DIGIT: [0-9];

/// intpart       ::=  digit+
fragment INT_PART: DIGIT+;