function f = my_erf(b)

x = linspace(0,b,1+floor(100*b));

f = exp(-x.^2);

f = 2/sqrt(pi)*int_SC(x,f);