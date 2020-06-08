tol = 0.0001;
Nmax = 20000;
N = 1;
A = 0;

while abs(pi-4*A) > tol && N < Nmax
    x = linspace(0,1,N);
    y = sqrt(1-x.^2);
    A = int_SC(x,y);
    N = N+1
end

disp('El area aproximada del circulo es: '), disp(4*A)
disp('El numero de puntos utilizados es:'), disp(N)

clear all