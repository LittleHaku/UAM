
N = input('Dame un numero natural N: ');
nodos = linspace(-5,5,N);
valores = 1./(1+nodos.^2);

x = [-5:0.001:5];
yRun = 1./(1+x.^2);
ypol = newton(nodos,valores,x);

plot(x,yRun,x,ypol,nodos,valores,'r*') % https://es.mathworks.com/help/matlab/ref/plot.html
axis([-5 5 -2 8])
