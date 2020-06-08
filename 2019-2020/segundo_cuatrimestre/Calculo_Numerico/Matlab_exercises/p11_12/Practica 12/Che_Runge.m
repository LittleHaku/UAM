
N = input('Dame un numero natural N: ');
i = 0:N-1;
nodos = cos((2*i+1)*pi/2/N);
valores = 1./(1+(5*nodos).^2);

x = [-1:0.001:1];
yRun = 1./(1+(5*x).^2);
ypol = newton(nodos,valores,x);

plot(x,yRun,x,ypol,nodos,valores,'r*') % https://es.mathworks.com/help/matlab/ref/plot.html
axis([-1 1 -2/5 8/5])
