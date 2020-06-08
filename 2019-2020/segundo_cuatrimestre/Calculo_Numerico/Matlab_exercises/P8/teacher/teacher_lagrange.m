function [Pol] = lagrange(x, y, z)
% Esta función calcula el polinomio de interpolación que pasa por los nodos
% (x_i, y_i), i=0,...,n, donde x=[x_0... , x_n] e y=[y_0,... , y_n], en 
% el punto z, con el método de Lagrange 

n = length(x);
s = 0; % Inicializamos la suma a 0
for k = 1:n
    p = 1; % Inicializamos el producto a 1
    for j = [1:k-1, k+1:n]
        p = p.*(z - x(j))./(x(k) - x(j)); % Calculamos el producto p 
    end
    s = s + y(k)*p; % Calculamos la suma s
end
Pol = s; % Asignamos el valor calculado a Pol
end

