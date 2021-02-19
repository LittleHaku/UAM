N = 5000
mi_fun <- function() {
  (rnorm(1) + rnorm(1) + rnorm(1))^2 + (rnorm(1) + rnorm(1) + rnorm(1))^2
}

v <- replicate(N, mi_fun())
sum = 0
for(el in v) {
  if(el > 14) { # PROBABILIDAD DE X > 2
    sum = sum + 1
  }
}
print(sum/N)
