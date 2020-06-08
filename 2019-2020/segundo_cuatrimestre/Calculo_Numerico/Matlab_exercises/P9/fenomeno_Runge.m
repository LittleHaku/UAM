%n = input('escriba n: ');

n = 9;
interval = linspace(-5, 5, n);
f = @(x) 1./(1+x.^2);
g = @(x) newton(interval, f(interval), x);

fplot(f, [-5, 5])
hold on
fplot(g, [-5, 5])
plot(interval, f(interval), 'r*')
hold off