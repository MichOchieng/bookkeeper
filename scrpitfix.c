/*
#if 0
 * $Header: /source/script/scriptfix/RCS/scriptfix.c,v 1.7 1993/09/07 16:34:34 Exp $
#endif
 */
/*
 *  scriptfix.c
 *  ===========
 *
 *  Usage:
 *  scriptfix  [infile] ...
 *
 *  Make script output files suitable for printing, and editing.
 *
 *  1) Remove backspace characters, and with each
 *     backspace one preceeding character, so that the printed
 *     output will look like the screen output.
 *
 *  2) Remove carriage returns that precede a newline so the
 *     file will look right in the editor, and will pass correctly
 *     through text processing programs such as troff.
 *
 *  3) Remove the control characters and escape sequences
 *     currently used in prompt strings.
 *
 *  Scriptfix will processes the named files in order and produce
 *  its output on the standard output.  If no files are named
 *  then scriptfix will read from standard input.
 *
 *  BUGS:
 *  If input lines are longer than 1024 characters, backspaces may
 *  not be handled correctly.
 */

# include <stdio.h>
# include <stdlib.h>

# define BUFFMAX        1024
# define BUFFSIZE       BUFFMAX + 2        /* 0 to buffmax + '\0' */

# define BELL           '\007'             /* Ring the bell. */
# define CTRL_N         '\016'             /* Highlight output. */
# define CTRL_O         '\017'             /* Unhighlight output. */
# define ESCAPE         '\033'             /* Escape character. */
# define SEMICOLON      ';'


static
void doit( in_fp )
    register FILE *in_fp;
{
    char buffer[BUFFSIZE];
    register int  inch, pos = 0;

    /* Read all input until end-of-file. */
    while ((inch = getc(in_fp)) != EOF) {
        switch (inch)
        {
        /* Process backspace characters. */
        case '\b':
            /* Simply backup in buffer, but do not pass front. */
            /* This will strictly reproduce the screen. */
            if ((--pos) < 0) pos = 0;
            break;

        /* Delete CTRL/M (carriage return) followed by a new line. */
        case '\n':
            if (pos > 0  &&  buffer[pos-1] == '\015')
                    pos--;
            buffer[pos++] = inch;
            buffer[pos] = '\0';
            fputs (buffer, stdout);
            pos = 0;
            break;

        /* Some control characters should be ignored. */
        case CTRL_O:
        case CTRL_N:
        case BELL:
        case '\0':        /* used for padding */
            break;

        /* Remove the common escape sequences. */
        /* These are used in the default MFCF prompt. */
        case ESCAPE:
            inch = getc(in_fp);
            if (inch == EOF) break;

            /* Processes the characters following the escape. */
            switch (inch)
            {
            case '[':
                /* An escape sequence beginning with '[' */
                /* ends with an alphabetic character. */
                do {
                    inch = getc(in_fp);
                    if (inch == EOF) return;
                } while ( !(   inch >= 'A'  &&  inch <= 'Z'
                            || inch >= 'a'  &&  inch <= 'z'));
                break;

            case 'Y':
                /* An escape Y is followed by two bytes of */
                /* cursor address.  Skip those characters. */
                if (getc(in_fp) == EOF) return;
                if (getc(in_fp) == EOF) return;
                break;
            
            case '!':
            case '(':
            case ')':
            case '#':
                /* An escape followed by !, (, ), or # is also */
                /* followed by another single character. */
                if (getc(in_fp) == EOF) return;
                break;
            

            default:
                /* For all other escape sequences, skip one character. */
                break;
            }
            break;

        /* All other characters copied to buffer unchanged. */
        default:
            buffer[pos] = inch;
            if ((++pos) > BUFFMAX)
            {
                buffer[pos] = '\0';
                fputs (buffer, stdout);
                pos = 0;
            }
            break;
        }
    }
}

main ( argc, argv )
    int argc;
    char *argv[];
{
    FILE *in_fp;
    register int i, errs=0;
    
    if ( argc == 1 ) {
        doit( stdin );
    } else {
        for ( i=1; i<argc; i++ ) {
            if ( (in_fp=fopen(argv[i],"r")) == (FILE *)NULL ) {
                fprintf(stderr,"%s: can't open '%s': ", argv[0], argv[i] );
                perror( "" );
                errs++;
            } else {
                doit ( in_fp );
                (void) fclose ( in_fp );
            }
        }
    }
    exit(errs);
}
