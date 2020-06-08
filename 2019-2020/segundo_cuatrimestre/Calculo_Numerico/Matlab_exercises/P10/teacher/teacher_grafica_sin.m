
% Este script dibuja la gráfica en [0,10] de sin(x) y del polinomio interpolador del sin(x) 
% en los nodos x = [i*pi/2: i = 0, ..., 4] 

x = [0, pi/2, pi, 3*pi/2, 2*pi]; % Nodos
y = sin(x); % Valores
f = @(z) [sin(z), lagrange(x, y, z)]; % https://es.mathworks.com/help/matlab/matlab_prog/anonymous-functions.html
fplot (f, [0, 10]); % https://es.mathworks.com/help/matlab/ref/fplot.html