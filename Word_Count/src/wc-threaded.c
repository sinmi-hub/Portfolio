/*Author: Sinmi*/

/* Counts the number of words, lines, and characters in the files whose
 * names are given as command-line arguments.  If there are no command-line
 * arguments then the line, word, and character counts will just be 0.
 * Mimics the effects of the UNIX "wc" utility, although does not have
 * exactly the same behavior in all cases. Specifically implememnted with
 * concurrency as multiple threads are created to process words, lines and
 * characters in the files whose names are given as cmd-line arguments. i.e.
 * one thread for each argument
 */

#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>

static void *analyze_files(void  *arg);

/*creating a structure to be used by the thread to process
files*/
typedef struct{
    char *filename;
    int total_lines;
    int total_words;
    int  total_chars;
    FILE *fp;/*FILE *ptr for each thread to maintain its open file*/
} Thread;

/*This struct will store the return value of each thread after they
are called in main. Each return value of thread will simply be stored
in this struct and can be accessed wherever needed*/
typedef struct{
    int lines;
    int words;
    int chars;
} Results;

/*creating a function that will be used by the threads to process the files.
Since calling a thread requires the function, we create a function that
imply processes files.*/
static void *analyze_files(void *arg){
    /*Cast when using void pointers*/
    Thread *thread = (Thread *) arg;
    Results *thread_result = malloc(sizeof(Results));
    char ch, next_ch;
    int lines = 0, words = 0, chars = 0;

    /*echoing error if memory allocation fails*/
    if (thread_result == NULL){
        perror("Memory allocation failed: ");
        exit(0);
    }
   
    /*initializing fields of threads*/
    thread->total_lines = 0;
    thread->total_chars = 0;
    thread->total_words = 0;

    thread->fp= fopen(thread->filename, "r");  /* open that file */

    /* silently ignore any problems trying to open files */
    if (thread->fp != NULL) 
    {
        /* read each file one character at a time, until EOF */
        ch= fgetc(thread->fp);

        while (!feof(thread->fp)) {
            next_ch= fgetc(thread->fp);  /* look ahead and get the next character */
            ungetc(next_ch, thread->fp);  /* unread the next character*/

            /* update the counts as needed every time a character is read */

            /* a newline means the line count increases */
            if (ch == '\n') 
                lines++;

            /* if the current character is not whitespace but the next char
            is, or if the current character is not whitespace and it is the
            last character in the input, the word count increases */
            if (!isspace(ch) && (isspace(next_ch) || feof(thread->fp)))
                words++;

            /* increasing the character count is a no-brainer */
            chars++;
            ch= fgetc(thread->fp);
        }    

        /* add the totals for the current file into the accumulating totals
        for all files. This will end up being 0 if file didnt exist as
        lines, words and chars will also be 0 */
        thread->total_lines += lines;
        thread->total_words += words;
        thread->total_chars += chars;

        fclose(thread->fp);
    }

    thread_result->lines = thread->total_lines;
    thread_result->words = thread->total_words;
    thread_result->chars = thread->total_chars;

    /*This will enable pthread_join to retrieve return value of thread*/
    pthread_exit (thread_result);
}

int main(int argc, char *argv[]) {
    int all_lines = 0, all_words = 0, all_chars = 0;
    int i = 0;
    /*Subtracting 1 to remove program name from argc*/
    int num_threads = argc - 1;
    /*array of "num_threads" amount of threads.*/
    pthread_t *tids;
    /*array of pointers that each individual pointer
    points to a thread structure that will be used to
    process each file */
    Thread **thread;
    Results **thread_results;/*holds return value of each thread*/
   
    /*Skipping program name*/
    argv = argv + 1;

    /*Dynamically allocating spacde for tids and threads and results*/
    tids = calloc(num_threads, sizeof(pthread_t));
    thread = calloc(num_threads + 1, sizeof(Thread *));
    thread_results = calloc(num_threads, sizeof(Results *));

    /*Echoing memory allocation failure*/
    if (tids == NULL || thread == NULL || thread_results == NULL) {
        perror("Memory allocation failed: ");
        exit(0);
    }

    /*creating the threads. Using one thread for every cmd line
    argument present*/
    for (i = 0; i < num_threads; i++){
        /*Allocating memory for each thread structure*/
        thread[i] = calloc(1, sizeof(Thread));
        /* Points the 'filename' field of the Thread 
        structure to the char* variable */
        thread[i]->filename = argv[i];
        pthread_create(&tids[i], NULL, analyze_files, thread[i]);
    }

    /*joining thread. Here we take the total line, char and words
    that each thread read and return it. Observe that all of this
    has been stored in the Result structure when pthread_create was
    called. Also for every iteration of pthread_create that is called,
    a result structure is dynamically allocated as needed. This is why
    we do not allocate in main again*/
    for (i = 0; i < num_threads; i++){
        /* Passing results[i] by reference creates (void **) */
        pthread_join(tids[i], (void **)&thread_results[i]);
        all_lines += thread_results[i]->lines;
        all_chars += thread_results[i]->chars;
        all_words += thread_results[i]->words;

        /*frees the result structure that is made at every iteration of pthread_create*/
        free(thread_results[i]);
    }

    printf("%4d %4d %4d\n", all_lines, all_words, all_chars);

    /*Freeing all dynamically allocated memory*/
    free(tids);
    tids = NULL;
    free(thread_results);
    thread_results = NULL;

    /*Each field of thread was dynamically allocated*/
    i = 0;

    while (thread[i] != NULL) {
      free(thread[i]);
      thread[i] = NULL;/*sets the pointer to that memory to NULL*/
      ++i;
    }

    /*frees thread itself*/
    free(thread);
    thread = NULL;

    return 0;
}
