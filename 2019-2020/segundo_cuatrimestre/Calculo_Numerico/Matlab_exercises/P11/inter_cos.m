%n = input('escriba n: ');

n = 4;
interval = linspace(0, 2*pi, n);
f = @(x) cos(x);
g = @(x) newton(interval, f(interval), x);


fplot(f, [0, 2*pi])
hold on
fplot(g, [0, 2*pi])
plot(interval, f(interval), 'r*')
%axis([-5 5 -2 7]);
hold off