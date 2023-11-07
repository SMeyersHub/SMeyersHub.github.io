#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "deq.h"
#include "error.h"

// indices and size of array of node pointers
typedef enum {Head,Tail,Ends} End;

typedef struct Node {
  struct Node *np[Ends];		// next/prev neighbors
  Data data;
} *Node;

typedef struct {
  Node ht[Ends];			// head/tail nodes
  int len;
} *Rep;

static Rep rep(Deq q) {
  if (!q) ERROR("zero pointer");
  return (Rep)q;
}

static void put(Rep r, End e, Data d) {
  Node newNode=(Node)malloc(sizeof(*newNode)); //Create a new node.
  
  if(r->len == 0) { //Is first node
    newNode->data = d; //Set the data for the new node to d.
    newNode->np[Head]=0; //Set the previous node as 0.
    newNode->np[Tail]=0; //Set the next node as 0.
    r->ht[Head] = newNode; //Set the head node to the new node
    r->ht[Tail] = newNode; //Set the tail node to the new node
  } 
  else { //Is head or tail
    newNode->data = d; //Set the data for the new node to d.
    newNode->np[e]=0; //Set the previous or next node as 0.
    newNode->np[!e]=r->ht[e]; //Set the next node as the previous head.
    r->ht[e]->np[e]=newNode; //Set the head/tail of the existing head/tail to the new head/tail
    r->ht[e] = newNode; //Set the head/tail node to the new node
  } 
  
  r->len+=1;
}

static Data ith(Rep r, End e, int i)  { 
    if(r->len == 0 || i >= r->len) {
	    printf("ith: Invalid node ithed.\n");
    } 
    else {
      int counter = 0;
      Data tempData = r->ht[e]->data; //Stores the data of the head/tail
      Node tempNode = r->ht[e]; //Sets the temp node as the head/tail
    
      while(counter < i) {
        tempNode = tempNode->np[!e]; //Sets the tempNode as the next node
        tempData = tempNode->data; //Sets the data as the tempNode's data
        counter++;
      }
      return tempData;
    }
	return 0; 
}

static Data get(Rep r, End e)         {
  if(r->len==1) { //If the node being got is the only node remaining.
     Data tempData = r->ht[e]->data; //Stores the data of the head/tail
     free(r->ht[e]); //Frees the node of the old head.
     r->ht[Head] = 0; //Sets the deq head as 0
     r->ht[Tail] = 0; //Sets the deq tail as 0
     r->len-=1;
     return tempData;
  } 
  else if(r->len>1) { //If there are more than 1 node (tail and head arent same node)
     Data tempData = r->ht[e]->data; //Stores the data of the head/tail
     r->ht[e]=r->ht[e]->np[!e]; //Moves the head/tail to one forward/back
     r->ht[e]->np[e]->np[!e]=0; //Breaks the connection between the current head/tail and the one being got
     free(r->ht[e]->np[e]); //Frees the node of the old head.
     r->ht[e]->np[e]=0; //breaks the connection between the old head/tail and the new one
     r->len-=1;
     return tempData;
  }
  
  return 0; 
}

static Data rem(Rep r, End e, Data d) { 
	if(r->len == 0) { //If the list has no data, print error message.
		printf("rem: Deque is empty, nothing to rem.\n");
	} else {
		int count = 0;
		Data tempData = r->ht[e]->data; //Set temp data as end e data
		Node tempNode = r->ht[e]; //Set temp node as end e node
		while(tempNode->np[!e] != 0 && tempData != d) { //Search through deq until either end is hit or temp data is found
			tempNode = tempNode->np[!e]; //Set temp node as next node away from end e
			tempData = tempNode->data; //Set temp data to new temp node data
			count++;
		}
		if(tempData == d) { //if the data is found, remove the node and return the data
			if(tempNode != r->ht[e] && tempNode != r->ht[!e]) {
				tempNode->np[e]->np[!e] = tempNode->np[!e];
				tempNode->np[!e]->np[e] = tempNode->np[e];
				free(tempNode);
				return tempData;
			} else if(tempNode == r->ht[e]) { //If the data is found at end e, get that end
				get(r, e);
			} else if(tempNode == r->ht[!e]) { //If th edata is found at the other end, get that end
				get(r, !e);
			}
		} else {
			printf("rem: Data does not exit in deq.\n");
		}
	}
	return 0; }

extern Deq deq_new() {
  Rep r=(Rep)malloc(sizeof(*r));
  if (!r) ERROR("malloc() failed");
  r->ht[Head]=0;
  r->ht[Tail]=0;
  r->len=0;
  return r;
}

extern int deq_len(Deq q) { return rep(q)->len; }

extern void deq_head_put(Deq q, Data d) {        put(rep(q),Head,d); }
extern Data deq_head_get(Deq q)         { return get(rep(q),Head); }
extern Data deq_head_ith(Deq q, int i)  { return ith(rep(q),Head,i); }
extern Data deq_head_rem(Deq q, Data d) { return rem(rep(q),Head,d); }

extern void deq_tail_put(Deq q, Data d) {        put(rep(q),Tail,d); }
extern Data deq_tail_get(Deq q)         { return get(rep(q),Tail); }
extern Data deq_tail_ith(Deq q, int i)  { return ith(rep(q),Tail,i); }
extern Data deq_tail_rem(Deq q, Data d) { return rem(rep(q),Tail,d); }

extern void deq_map(Deq q, DeqMapF f) {
  for (Node n=rep(q)->ht[Head]; n; n=n->np[Tail])
    f(n->data);
}

extern void deq_del(Deq q, DeqMapF f) {
  if (f) deq_map(q,f);
  Node curr=rep(q)->ht[Head];
  while (curr) {
    Node next=curr->np[Tail];
    free(curr);
    curr=next;
  }
  free(q);
}

extern Str deq_str(Deq q, DeqStrF f) {
  char *s=strdup("");
  for (Node n=rep(q)->ht[Head]; n; n=n->np[Tail]) {
    char *d=f ? f(n->data) : n->data;
    char *t; asprintf(&t,"%s%s%s",s,(*s ? " " : ""),d);
    free(s); s=t;
    if (f) free(d);
  }
  return s;
}
