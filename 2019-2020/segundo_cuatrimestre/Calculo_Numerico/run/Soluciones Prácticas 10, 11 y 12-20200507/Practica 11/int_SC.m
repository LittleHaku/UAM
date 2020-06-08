function I = int_SC(x,f)

n = length(x);
I = 0;
for i = 1:2:n-2
    I = I + int_S(x(i),x(i+2),f(i),f(i+1),f(i+2));
end