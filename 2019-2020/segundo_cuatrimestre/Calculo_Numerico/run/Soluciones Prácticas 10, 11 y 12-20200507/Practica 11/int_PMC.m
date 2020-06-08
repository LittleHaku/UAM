function I = int_PMC(x,f)

n = length(x);
I = 0;
for i = 1:n-1
    I = I + int_PM(x(i),x(i+1),f(i));
end