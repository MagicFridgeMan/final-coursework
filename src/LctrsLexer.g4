/*
* The subset of python rules used within my small LCTRS program.
*/

lexer grammar LctrsLexer;

// All comments that start with "///" are copy-pasted from
// The Python Language Reference

tokens {
    INDENT,
    DEDENT
}

options {
    superClass = Python3LexerBase;
}

/*
 * lexer rules
 */

INT: 'int';

DEF        : 'def';
ELSE       : 'else';
IF         : 'if';
RETURN     : 'return';
WHILE      : 'while';

NEWLINE: ({this.atStartOfInput()}? SPACES | ( '\r'? '\n' | '\r' | '\f') SPACES?) {this.onNewLine();};

SKIP_: SPACES -> skip;

IDENTIFIER: LETTER+ '_' LETTER+;

OPEN_PAREN         : '(' {this.openBrace();};
CLOSE_PAREN        : ')' {this.closeBrace();};
COLON              : ':';
SEMI_COLON         : ';';
COMMA              : ',';
ARROW              : '->';
ASSIGN             : '=';
ADD                : '+';
SUB                : '-';
MUL                : '*';
MOD                : '%';
IDIV               : '//';
LESS_THAN          : '<';
GREATER_THAN       : '>';
EQUALS             : '==';
NOT_EQUALS         : '!=';
GT_EQ              : '>=';
LT_EQ              : '<=';


/// decimalinteger ::=  nonzerodigit digit* | "0"+
INTEGER: [0-9]+;

/*
 * fragments
 */

// Fragment letters for each
fragment LETTER: [a-z] | [A-Z];

fragment SPACES: [ \t]+;
