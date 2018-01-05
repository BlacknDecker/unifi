# set the repo root path before importing data!!!

setwd("C:/simone_robamia/unifi/datamining-class-homework/")
students <- read.csv("res/TRE ANNI IMMATRICOLATI 2010-2013 PER STUDENTI.csv")

# gather some info about the imported dataset

str(students)

# ADJUST DATA ATTRIBUTES 
# convert "coorte" to nominal by making it a factor 

students[, c(1)] <- sapply(students[, c(1)], factor)

# ok, let's take a look at it now
str(students)
summary(students)

#################
# SCATTER PLOTS #
#################
colors <- c("blue","red", "green", "orange")
coorte_labels <- students[,1]
coorte_colors <- colors[as.numeric(coorte_labels)]

# general attributes
students_subset1 <- students[,-c(1, 6 : 45)]
pairs(students_subset1, col = coorte_colors,lower.panel = NULL,cex.labelsiris=2, pch=19, cex = 1.2)
par(xpd = TRUE)
legend(x = 0.05, y = 0.4, cex = 1,legend = as.character(levels(coorte_labels)),fill = unique(coorte_colors))
par(xpd = NA)

# test / avg mark detail
test_score <- students[,2]
avg_mark <- students[,5]
plot(jitter(test_score), jitter(avg_mark), col = coorte_colors, pch=19, cex = 1, xlab="Entry Test Score", ylab="Avg. Exam Score")
legend(x="topleft", legend = levels(students[,1]), col=colors, pch=19)

# cfu / avg mark detail
cfu <- students[,4]
plot(jitter(cfu), jitter(avg_mark), col = coorte_colors, pch=19, cex = 1, xlab="Total Marked CFUs", ylab="Avg. Exam Score")
legend(x="bottomright", legend = levels(students[,1]), col=colors, pch=19)

# general first year exams performances
students_subset2 <- students[,-c(1 : 5, 7, 9, 11, 13, 15 : 45)]
pairs(students_subset2, col = coorte_colors,lower.panel = NULL,cex.labelsiris=2, pch=19, cex = 1.2)
par(xpd = TRUE)
legend(x = 0.05, y = 0.4, cex = 1,legend = as.character(levels(coorte_labels)),fill = unique(coorte_colors))
par(xpd = NA)

# first year "difficult" exams / total cfu detail
mdl <- students[,14]
plot(jitter(mdl), jitter(cfu), col = coorte_colors, pch=19, cex = 1, xlab="M.D.L. Exam Score", ylab="Total Marked CFUs")
legend(x="bottomright", legend = levels(students[,1]), col=colors, pch=19)
ade <- students[,8]
plot(jitter(ade), jitter(cfu), col = coorte_colors, pch=19, cex = 1, xlab="Computers Architecture Exam Score", ylab="Total Marked CFUs")
legend(x="bottomright", legend = levels(students[,1]), col=colors, pch=19)

# computer science exams performances
students_subset3 <- students[,c(6,8,10,22,24,26,32,36,42)]
pairs(students_subset3, col = coorte_colors,lower.panel = NULL,cex.labelsiris=2, pch=19, cex = 0.8)
par(xpd = TRUE)
legend(x = 0.05, y = 0.4, cex = 1,legend = as.character(levels(coorte_labels)),fill = unique(coorte_colors))
par(xpd = NA)

# general mathy exams performances
students_subset4 <- students[,c(12,14,18,20,28,30,34)]
pairs(students_subset4, col = coorte_colors,lower.panel = NULL,cex.labelsiris=2, pch=19, cex = 0.8)
par(xpd = TRUE)
legend(x = 0.05, y = 0.4, cex = 1,legend = as.character(levels(coorte_labels)),fill = unique(coorte_colors))
par(xpd = NA)

#################
### BOX PLOTS ###
#################

boxplot(students[,4]~students[,1],data = students,xlab="Total Marked CFU",col=colors)
boxplot(students[,5]~students[,1],data = students,xlab="Avg. Exam Score",col=colors)
