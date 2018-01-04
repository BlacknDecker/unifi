iris <- datasets::iris
summary(iris)
summary(iris$Sepal.Length)

#Scatter plots
iris2 <- iris[,-5]
species_labels <- iris[,5]
colors <- c("blue","red", "green")
species_col <- colors[as.numeric(species_labels)]
plot(iris,col = species_col)

SepalLength<-iris[,1]
SepalWidth<-iris[,2]
plot(SepalLength,SepalWidth,col = species_col, pch=19,cex = 1.1,xlab="Sepal Length",ylab="Sepal Width")

PetalLength<-iris[,3]
PetalWidth<-iris[,4]
plot(PetalLength,PetalWidth,col = species_col,pch=19,cex = 1.1,xlab="Petal Length",ylab="Petal Width")

#Attenzione al percorso del file
datioutliers=read.csv2("C:/Users/Donatella/Documents/My Dropbox/Data Mining & Organization/Lucidi miei/Iris and other datasets/DatiIrisOutliers.csv", dec=".",sep=",")
summary(datioutliers)
PetalLengthO<-datioutliers[,3]
PetalWidthO<-datioutliers[,4]
species_labelsO <- datioutliers[,5]
species_colO <- colors[as.numeric(species_labelsO)]
plot(PetalLengthO,PetalWidthO,col = species_colO,pch=19,cex = 1.1,xlab="Petal Length",ylab="Petal Width")

pairs(iris2, col = species_col,lower.panel = NULL,cex.labelsiris=2, pch=19, cex = 1.2)
par(xpd = TRUE)
legend(x = 0.05, y = 0.4, cex = 2,legend = as.character(levels(species_labels)),fill = unique(species_col))
par(xpd = NA)

#Boxplots
boxplot(Sepal.Length~Species,data = iris,xlab="Sepal.Length",col=c("blue","red", "green"))

boxplot(iris[,1],xlab="Sepal.Length",ylab="Length",main="Summary Charateristics of Sepal.Length")

#Histograms
hist(SepalWidth)

#Alternative scatter plot matrix
library("GGally")
ggpairs(iris, ggplot2::aes(colour=Species))

#Parallel coordinates
par(las = 1, mar = c(4.5, 3, 3, 2) + 0.1, cex = .8)
MASS::parcoord(iris2, col = species_col, var.label = TRUE, lwd = 2)
# Add Title
title("Parallel coordinates plot of the Iris data")
# Add a legend
par(xpd = TRUE)
legend(x = 1.75, y = -.13, cex = 1,legend = as.character(levels(species_labels)),fill = unique(species_col), horiz = TRUE)
par(xpd = NA)

#Iris data matrix visualization
iris_matrix <- as.matrix(iris[,1:4])
library(seriation) ## for pimage
iris_scaled <- scale(iris_matrix)
# values smaller than the average are blue and larger ones are red
pimage(iris_scaled,ylab="Object (ordered by species)",main="Standard deviations from the feature mean")

iris_matrix <- as.matrix(iris[,1:4])
library(seriation) ## for pimage
# Correlation between objects
cm2 <- cor(t(iris_matrix))
pimage(cm2,main="Correlation matrix", xlab="Objects", ylab="Objects",zlim = c(-1,1),col = bluered(100))

pimage(cm2,main="Correlation matrix", xlab="Objects", ylab="Objects",zlim = c(0.4,1),col = greenred(100))

#Proving similar behaviour of attributes
Pearsoncorrelation<-cor(iris2,method="pearson")
Pearsoncorrelation
pimage(Pearsoncorrelation)

Spearmancorrelation<-cor(iris2,method="spearman")
Spearmancorrelation