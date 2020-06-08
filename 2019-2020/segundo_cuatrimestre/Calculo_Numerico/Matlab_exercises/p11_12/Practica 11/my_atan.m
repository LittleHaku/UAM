function f = my_atan(b)

x = linspace(0,b,1+floor(100*b));

f = 1./(1+x.^2);

f = int_SC(x,f);