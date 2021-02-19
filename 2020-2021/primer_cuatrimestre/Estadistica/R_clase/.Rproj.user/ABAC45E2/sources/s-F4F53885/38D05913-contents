library(tidyverse) # ggplot.
#library(GGally) # ggpairs.
#library(ggridges) # geom_density_ridges.
#library(gapminder) # contiene un data set de paises.
#library(patchwork) # muestra 2 plots: plot1 / plot2.

link <- "https://matematicas.uam.es/~joser.berrendero/datos/mercurio.txt"

df <- read.table(link, header = TRUE)
#dato <- data.frame(x = rbeta(5000, shape1 = 3, shape2=6))
#ggplot(df) + geom_histogram(aes(x = V1))
#ggplot(df) + geom_density(aes(x = CONC)) + stat_function(fun = dnorm, args = list(xmed, xvar))

get_interval <- function(datos, nivel_confianza = 0.95) {
  xmed <- mean(datos)
  xsd <- sd(datos)
  xn <- length(datos)
  xalpha <- 1-nivel_confianza
  xz <- qt(1-xalpha/2, df = xn-1) # para poblacion normal, en general qnorm(1-xalpha/2)
  c(xmed - xz*xsd/sqrt(xn), xmed + xz*xsd/sqrt(xn))
}

#xvar <- var(df$CONC)
#xmed <- mean(df$CONC)
#ggplot(df) +
#  geom_density(aes(x = CONC)) +
#  geom_function(fun = dnorm, args = list(xmed, xvar)) +
#  xlim(-2, 4)

rnorm(30)

df <- data.frame(x = c(-1, 2))
ggplot(df) +
  geom_function(aes(x),fun = dunif)

x <- c(1, 12, 3)
x
