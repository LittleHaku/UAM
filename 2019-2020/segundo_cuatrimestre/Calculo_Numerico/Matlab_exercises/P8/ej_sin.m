
a = 1:5;
b = 1:5;
format long
for i = 1:5
    a(i) = (i-1)*pi/2;
    b(i) = sin((i-1)*pi/2);
end

f = @(x) Lagrange(a, b, x);
fplot(f, [0, 10])
hold on
g = @(x) sin(x);
fplot(g, [0, 10])
hold off
