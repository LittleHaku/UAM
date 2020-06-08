
N = input('Dame un numero natural N: ');
nodos = linspace(0,2*pi,N); 
valores = cos(nodos);

x = [0:0.001:2*pi];
ycos = cos(x);
ypol = newton(nodos,valores,x);

plot(x,ycos,x,ypol,nodos,valores,'r*') % https://es.mathworks.com/help/matlab/ref/plot.html
axis([0 2*pi -pi pi])