
mi_f <- function(x) {
  3*dnorm(x)
}

mi_f2 <- function(x) {
  #dnorm(x, 0, 1) + dnorm(x, 0, 1) + dnorm(x, 0, 1)
  rnorm(x, 0, 3)  
}

ggplot(data.frame(x=c(-5:5)), aes(x = x)) +
  stat_function(fun = mi_f) +
  stat_function(fun = mi_f2)
