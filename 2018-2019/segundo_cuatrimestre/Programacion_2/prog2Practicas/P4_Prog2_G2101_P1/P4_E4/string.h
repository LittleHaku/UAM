#ifndef STRING_H
#define STRING_H

#define MAX_STR 1024

void cadDestroy(void *);
void * cadCopy(const void *);
int cadPrint(FILE *, const void *);
int cadCmp(const void*, const void*);


#endif
