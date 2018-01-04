# set the repo root path before importing data!!!

setwd("C:/simone_robamia/unifi/datamining-class-homework/")
students <- read.csv("res/TRE ANNI IMMATRICOLATI 2010-2013 PER STUDENTI.csv")

# gather some info about the imported dataset

str(students)

# ADJUST DATA ATTRIBUTES 
# convert "coorte" to nominal by making it a factor 

students[, c(1)] <- sapply(students[, c(1)], factor)


#ok, let's take a look at it now
str(students)
summary(students)

# SCATTER PLOTS
colors <- c("blue","red", "green", "orange")
coorte_labels <- students[,1]
coorte_colors <- colors[as.numeric(coorte_labels)]
+
test_score <- students[,2]
final_score <- students[,5]
plot(jitter(test_score), jitter(final_score), col = coorte_colors, pch=19, cex = 1, xlab="Entry Test Score", ylab="Avg. Exam Score")
legend(x="topleft", legend = levels(students[,1]), col=colors, pch=19)
