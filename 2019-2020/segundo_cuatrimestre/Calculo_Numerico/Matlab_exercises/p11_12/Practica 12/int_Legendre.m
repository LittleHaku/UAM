function y = int_Legendre(f0,f1,f2)

% Recla de cuadratura con polinomios de Legendre
% Los nodos son x0 = 0, x1 = sqrt(3/5), x2 = -sqrt(3/5)
% Los pesos son alpha0 = 8/9, alpha1 = alpha2 = 5/9 

y = (8*f0 + 5*(f1+f2))/9;

