
function [Pol] = newton(x,y,z)
% Evaluamos el polonomio de interpolacion en el nuevo punto z.

n = length(y);

c = diferencias(x,y); % Calculamos los coeficientes mediante diferencias.m

Pol = c(n); % Inicializamos Pol
for j = n-1:-1:1
    Pol = c(j) + Pol.*(z-x(j)); % Calculamos Pol mediante el metodo de Horner
end