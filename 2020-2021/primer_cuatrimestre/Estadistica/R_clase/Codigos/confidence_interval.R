get_interval <- function(datos, nivel_confianza = 0.95) {
  xmed <- mean(datos)
  xsd <- sd(datos)
  xn <- length(datos)
  xalpha <- 1-nivel_confianza
  xz <- qt(1-xalpha/2, df = xn-1) # para poblacion normal, en general qnorm(1-xalpha/2)
  c(xmed - xz*xsd/sqrt(xn), xmed + xz*xsd/sqrt(xn))
}
