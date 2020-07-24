library(RMySQL)
library(dplyr)
con <- dbConnect(MySQL(),user="apatta",password="apatta@db",dbname="apatta",host="172.19.200.55")
rs <- dbSendQuery(con,"select * from datadict")
data <- fetch(rs)
dbClearResult(rs)
dbDisconnect(con)
keydata <- data %>% distinct(keywords)
View(keydata)
for(i in 1:nrow(keydata)){
  con <- dbConnect(MySQL(),user="apatta",password="apatta@db",dbname="apatta",host="172.19.200.55")
  keywords <- keydata[i,"keywords"]
  sql <- sprintf("SELECT keywords, synonyms, message, reviewer, author, created_on FROM tempOpenStack WHERE keywords = '%s' AND YEAR(created_on) = %s",keywords,"2014")
  rs <- dbSendQuery(con,sql)
  data <- fetch(rs)
  write.table(data, "myDF.csv", sep = ",", col.names = !file.exists("myDF.csv"), append = T)
  dbClearResult(rs)
  dbDisconnect(con)
}
OpenStack <- read.csv("myDF.csv")
OpenStack.txt <- OpenStack$message
OpenStack.txt<-paste(OpenStack.txt,collapse = " ")

stop_words <- stopwords("SMART")
reviews <- OpenStack.txt
reviews <- gsub("'", "", reviews)  # remove apostrophes
reviews <- gsub("[[:punct:]]", " ", reviews)  # replace punctuation with space
reviews <- gsub("[[:cntrl:]]", " ", reviews)  # replace control characters with space
reviews <- gsub("^[[:space:]]+", "", reviews) # remove whitespace at beginning of documents
reviews <- gsub("[[:space:]]+$", "", reviews) # remove whitespace at end of documents
reviews <- gsub("http[^[:space:]]*","",reviews) # remove URLs
reviews <- gsub("[[:digit]]","",reviews) #remove digits

# compute the table of terms:
term.table <- table(unlist(doc.list))
term.table <- sort(term.table, decreasing = TRUE)

# remove terms that are stop words or occur fewer than 5 times:
del <- names(term.table) %in% stop_words | term.table < 5
term.table <- term.table[!del]
vocab <- names(term.table)

# now put the documents into the format required by the lda package:
get.terms <- function(x) {
  index <- match(x, vocab)
  index <- index[!is.na(index)]
  rbind(as.integer(index - 1), as.integer(rep(1, length(index))))
}
documents <- lapply(doc.list, get.terms)
reviews <- tolower(reviews)  # force to lowercase

# tokenize on space and output as a list:
doc.list <- strsplit(reviews, "[[:space:]]+")

# compute the table of terms:
term.table <- table(unlist(doc.list))
term.table <- sort(term.table, decreasing = TRUE)

# remove terms that are stop words or occur fewer than 5 times:
del <- names(term.table) %in% stop_words | term.table < 5
term.table <- term.table[!del]
vocab <- names(term.table)

# now put the documents into the format required by the lda package:
get.terms <- function(x) {
  index <- match(x, vocab)
  index <- index[!is.na(index)]
  rbind(as.integer(index - 1), as.integer(rep(1, length(index))))
}
documents <- lapply(doc.list, get.terms)

library(lda)

# Compute some statistics related to the data set:
D <- length(documents)  # number of documents 
W <- length(vocab)  # number of terms in the vocab
doc.length <- sapply(documents, function(x) sum(x[2, ]))  # number of tokens per document 
N <- sum(doc.length)  # total number of tokens in the data 
term.frequency <- as.integer(term.table) # frequencies of terms in the corpus 

# MCMC and model tuning parameters:
K <- 20
G <- 5000
alpha <- 0.02
eta <- 0.02

# Fit the model:
library(lda)
set.seed(357)
ldaModel <- lda.collapsed.gibbs.sampler(documents = documents, K = K, vocab = vocab, 
                                   num.iterations = G, alpha = alpha, 
                                   eta = eta, initial = NULL, burnin = 0,
                                   compute.log.likelihood = TRUE)
top.words <- top.topic.words(ldaModel$topics, 10, by.score=TRUE)
