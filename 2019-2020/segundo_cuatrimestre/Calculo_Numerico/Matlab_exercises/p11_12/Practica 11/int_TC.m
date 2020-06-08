function I = int_TC(x,f)

n = length(x);
I = 0;
for i = 1:n-1
    I = I + int_T(x(i),x(i+1),f(i),f(i+1));
end